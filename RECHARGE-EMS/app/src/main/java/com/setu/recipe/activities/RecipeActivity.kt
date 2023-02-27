package com.setu.recipe.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeBinding
import com.setu.recipe.main.MainApp
import com.setu.recipe.models.RecipeModel
import org.json.JSONArray
import org.json.JSONObject

import timber.log.Timber.i
import java.io.File

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    var recipe = RecipeModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        val filesDir = this.filesDir

        if (intent.hasExtra("recipe_edit")) {
            recipe = intent.extras?.getParcelable("recipe_edit")!!
            binding.recipeTitle.setText(recipe.title)
            binding.description.setText(recipe.description)
            binding.instruction.setText(recipe.instructions)
        }

        /*binding.btnAdd.setOnClickListener() {
           i("add Button Pressed")
        }*/
        //Alternitive binding
        binding.btnAdd.setOnClickListener() {
            recipe.title = binding.recipeTitle.text.toString()
            recipe.description = binding.description.text.toString()
            recipe.instructions = binding.instruction.text.toString()
            if (recipe.title.isNotEmpty()) {
                app.recipes.create(recipe.copy())
                i("add Button Pressed: $recipe")

                // Create a JSONObject from the recipe object
                val jsonRecipe = JSONObject()
                jsonRecipe.put("title", recipe.title)
                jsonRecipe.put("description", recipe.description)
                jsonRecipe.put("instructions", recipe.instructions)

                // Write the JSONObject to a file
                val file = File(filesDir, "recipe.json")
                file.writeText(jsonRecipe.toString())

                i("JSON data written to file: ${jsonRecipe.toString()}")

                val filename = "recipe.json"
                val fileContents = applicationContext.openFileInput(filename).bufferedReader().use { it.readText() }
                i("File contents: $fileContents")

                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,R.string.enter_Title, Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
    private fun saveDataToJsonFile(context: Context, recipeList: List<RecipeModel>) {

        val gson = Gson()
        val jsonString = gson.toJson(recipeList)

        val file = File(context.filesDir, "recipe_data.json")
        file.writeText(jsonString)
    }

    fun logRecipeData(context: Context) {
        val file = File(context.filesDir, "recipe_data.json")
        if (file.exists()) {
            val json = file.readText()
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val title = jsonObject.getString("title")
                val description = jsonObject.getString("description")
                val image = jsonObject.getString("image")
                val ingredients = jsonObject.getString("ingredients")
                val steps = jsonObject.getString("steps")
                val rating = jsonObject.getDouble("rating")
                println("Title: $title, Description: $description, Image: $image, Ingredients: $ingredients, Steps: $steps, Rating: $rating")
            }
        } else {
            println("Recipe data file does not exist.")
        }
    }


    override fun onCreateOptionsMenu(cancel: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, cancel)
        return super.onCreateOptionsMenu(cancel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}