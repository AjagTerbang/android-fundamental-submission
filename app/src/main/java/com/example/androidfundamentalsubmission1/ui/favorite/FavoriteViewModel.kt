package com.example.androidfundamentalsubmission1.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidfundamentalsubmission1.database.AppDatabase
import com.example.androidfundamentalsubmission1.entity.FavoriteEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(private val database: AppDatabase): ViewModel() {

    val favoriteEvents: LiveData<List<FavoriteEvent>> get() = database.favoriteEventDao().getAllFavoriteEvent()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _loading.value = true
        favoriteEvents.observeForever {
            _loading.value = false
        }
    }


}