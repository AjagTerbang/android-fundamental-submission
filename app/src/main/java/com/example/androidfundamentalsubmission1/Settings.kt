package com.example.androidfundamentalsubmission1

import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.dataStoreFile
import androidx.lifecycle.ViewModelProvider
import com.example.androidfundamentalsubmission1.setting.MyApplication.Companion.dataStore
import com.example.androidfundamentalsubmission1.setting.SettingPreferences
import com.example.androidfundamentalsubmission1.setting.SettingViewModel
import com.example.androidfundamentalsubmission1.setting.ViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = SettingPreferences.getInstance(applicationContext.dataStore)

        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )


        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
           mainViewModel.saveThemeSetting(isChecked)
        }

    }

    companion object {
        fun newIntent(mainActivity: MainActivity): Intent {
            return Intent(mainActivity, Settings::class.java)
        }
    }


}