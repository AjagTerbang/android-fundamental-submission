package com.example.androidfundamentalsubmission1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentalsubmission1.adapter.EventAdapter
import com.example.androidfundamentalsubmission1.databinding.FragmentHomeBinding
import com.example.androidfundamentalsubmission1.model.eventUpcomming

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        val progressBar = binding.progressBar

        recyclerView.layoutManager = LinearLayoutManager(context)

       homeViewModel.events.observe(viewLifecycleOwner, { events->
           progressBar.visibility = View.GONE
            val adapter = EventAdapter(events.map { events ->
                eventUpcomming(events.id ?:-1 , events?.mediaCover.toString(), events?.name.toString())
            })
            recyclerView.adapter = adapter
        })

        homeViewModel.error.observe(viewLifecycleOwner, { error ->
            progressBar.visibility = View.GONE
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(error)
                .setPositiveButton("Ok", null)
                .show()
        })

        progressBar.visibility = View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}