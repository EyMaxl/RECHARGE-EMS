package com.setu.recipe.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityNavigationBinding
import com.setu.recipe.main.MainApp

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)


        binding.profile.setOnClickListener(){
            val launcherIntent = Intent(this, ProfileActivity::class.java)
            startActivity(launcherIntent)
            finish()
        }

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