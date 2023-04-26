package com.setu.recipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityLoginBinding
import com.setu.recipe.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    //lateinit var auth: FirebaseAuth
    //lateinit var user: FirebaseUser
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        //auth = FirebaseAuth.getInstance()
        var user = Firebase.auth.currentUser
        if (user == null){
            val launcherIntent = Intent(this, LoginActivity::class.java)
            startActivity(launcherIntent)
            finish()
        } else {
            binding.userDetails.setText(user.email)
        }
        binding.logout.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            val launcherIntent = Intent(this, LoginActivity::class.java)
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
                val launcherIntent = Intent(this, NavigationActivity::class.java)
                startActivity(launcherIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}