package com.example.androidfundamentalsubmission1.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidfundamentalsubmission1.database.AppDatabase

class FavoriteViewModelFactory(
    private val database: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}