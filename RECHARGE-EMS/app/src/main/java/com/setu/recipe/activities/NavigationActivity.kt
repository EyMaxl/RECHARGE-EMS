package com.setu.recipe.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityNavigationBinding
import com.setu.recipe.main.MainApp

class NavigationActivity : AppCompatActivity() {

    //lateinit var app: MainApp - online but local gone

    private val navigationCategories = arrayOf(
        "Profile",
        "Favorites",
        "Recipes",
        "Settings"
    )

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, navigationCategories)
        binding.navList.adapter = adapter


        binding.navList.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    val launcherIntent = Intent(this, ProfileActivity::class.java)
                    startActivity(launcherIntent)
                    finish()
                }
                1 -> { /* Implement notification preferences logic */ }
                2 -> { /* Implement account management logic */ }
                3 -> {
                    val launcherIntent = Intent(this, SettingsActivity::class.java)
                    startActivity(launcherIntent)
                    finish()
                }
            }
        }

/*
        binding.settings.setOnClickListener(){
            val launcherIntent = Intent(this, SettingsActivity::class.java)
            startActivity(launcherIntent)
            finish()
        }*/

        // app = application as MainApp - online but local gone


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_back, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_back -> {
                val launcherIntent = Intent(this, RecipeListActivity::class.java)
                startActivity(launcherIntent)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}