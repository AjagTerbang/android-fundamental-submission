package com.example.androidfundamentalsubmission1.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidfundamentalsubmission1.R
import com.example.androidfundamentalsubmission1.adapter.EventAdapter
import com.example.androidfundamentalsubmission1.adapter.FavoriteEventAdapter
import com.example.androidfundamentalsubmission1.database.AppDatabase
import com.example.androidfundamentalsubmission1.databinding.FragmentFavoriteBinding
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent


class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavoriteBinding

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(AppDatabase.getDatabase(requireContext()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteEventAdapter(emptyList())
        binding.favRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.favRecyclerView.adapter = adapter

        binding.favProgressBar.visibility = View.VISIBLE

        favoriteViewModel.favoriteEvents.observe(viewLifecycleOwner,  {  favEvents ->
            val items = arrayListOf<FavoriteEvent>()
            favEvents.map {
                val item = FavoriteEvent(id = it.id, name = it.name, mediaCover = it.mediaCover)
                items.add(item)
            }
            adapter.updateData(items)
        })

        favoriteViewModel.loading.observe(viewLifecycleOwner,  { isLoading ->
            binding.favProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

    }

}