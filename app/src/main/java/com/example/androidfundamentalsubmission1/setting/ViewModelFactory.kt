package com.example.androidfundamentalsubmission1.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences):  ViewModelProvider.NewInstanceFactory()  {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(pref) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}