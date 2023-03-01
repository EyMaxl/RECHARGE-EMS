package com.setu.recipe.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeBinding
import com.setu.recipe.databinding.ActivitySettingsBinding
import com.setu.recipe.main.MainApp

class SettingsActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        //binding.toolbar.setCollapseIcon(R.drawable.ic_launcher_foreground)

        app = application as MainApp

    }

}