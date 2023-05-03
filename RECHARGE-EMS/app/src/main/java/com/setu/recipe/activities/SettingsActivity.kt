package com.setu.recipe.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.setu.recipe.R
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.setu.recipe.databinding.ActivityNavigationBinding
import com.setu.recipe.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private val settingsCategories = arrayOf(
        "Dark Mode",
        "Notification Preferences",
        "Account Management",
        "Privacy Settings",
        "Language Settings",
        "Help & Support"
    )

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        val darkModeEnabled = loadDarkModePreference()
        setDarkModeEnabled(darkModeEnabled)

        val username: TextView = findViewById(R.id.username)

        // Set OnClickListener for the profile image
        binding.profileBar.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.settings_list_item,
            R.id.setting_title,
            settingsCategories
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                if (position == 0) {
                    val darkModeSwitch: Switch = view.findViewById(R.id.dark_mode_switch)
                    darkModeSwitch.visibility = View.VISIBLE
                    darkModeSwitch.isChecked = darkModeEnabled

                    darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                        setDarkModeEnabled(isChecked)
                        saveDarkModePreference(isChecked)
                    }
                }

                return view
            }
        }

        binding.settingsList.adapter = adapter

        binding.settingsList.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> { /* Implement dark mode logic */ }
                1 -> { /* Implement notification preferences logic */ }
                2 -> { /* Implement account management logic */ }
                3 -> { /* Implement privacy settings logic */ }
                4 -> { /* Implement language settings logic */ }
                5 -> { /* Implement help & support logic */ }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_back, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_back -> {
                val launcherIntent = Intent(this, NavigationActivity::class.java)
                startActivity(launcherIntent)

                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDarkModeEnabled(enabled: Boolean) {
        val mode = if (enabled) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
    private fun saveDarkModePreference(enabled: Boolean) {
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("dark_mode_enabled", enabled)
        editor.apply()
    }

    private fun loadDarkModePreference(): Boolean {
        val sharedPreferences = getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("dark_mode_enabled", false)
    }



}
