package com.example.androidfundamentalsubmission1.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentalsubmission1.adapter.EventAdapter
import com.example.androidfundamentalsubmission1.databinding.FragmentDashboardBinding
import com.example.androidfundamentalsubmission1.model.eventUpcomming

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.dashRecyclerView
        val progressBar = binding.dashProgressBar

        recyclerView.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.events.observe(viewLifecycleOwner, { events->
            progressBar.visibility = View.GONE
            val adapter = EventAdapter(events.map { events ->
                eventUpcomming(events?.id ?: -1 ,events?.mediaCover.toString(), events?.name.toString())
            })
            recyclerView.adapter = adapter
        })

        dashboardViewModel.error.observe(viewLifecycleOwner, { error ->
            progressBar.visibility = View.GONE
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(error)
                .setPositiveButton("Ok", null)
                .show()
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}