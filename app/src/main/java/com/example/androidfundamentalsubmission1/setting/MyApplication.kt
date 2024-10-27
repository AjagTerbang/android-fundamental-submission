package com.example.androidfundamentalsubmission1.setting

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

class MyApplication: Application() {
    companion object{
        val Context.dataStore by preferencesDataStore(name = "settings")
    }
}