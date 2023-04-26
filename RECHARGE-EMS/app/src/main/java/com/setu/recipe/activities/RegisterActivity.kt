package com.setu.recipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.quickstart.auth.kotlin.EmailPasswordActivity
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityLoginBinding
import com.setu.recipe.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val launcherIntent = Intent(this, RecipeListActivity::class.java)
            startActivity(launcherIntent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.LoginNow.setOnClickListener(){
            val launcherIntent = Intent(this, LoginActivity::class.java)
            startActivity(launcherIntent)
            finish()
        }
        binding.btnRegister.setOnClickListener() {
            binding.progressBar.visibility = View.VISIBLE
            var email = "";
            var password = "";
            email = binding.email.text.toString();
            password = binding.password.text.toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
                //return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
                //return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.progressBar.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        //Log.d(EmailPasswordActivity.TAG, "createUserWithEmail:success")
                        //val user = auth.currentUser
                        Toast.makeText(this, "Authentication created.",
                            Toast.LENGTH_SHORT).show()
                        val launcherIntent = Intent(this, ProfileActivity::class.java)
                        startActivity(launcherIntent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        //Log.w(EmailPasswordActivity.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }


        }
    }
}