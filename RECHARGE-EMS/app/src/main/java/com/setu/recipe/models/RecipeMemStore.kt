package com.setu.recipe.models

import android.content.Context
import com.google.gson.Gson
import timber.log.Timber.i
import java.io.BufferedReader
import java.io.InputStreamReader


private var lastId = 0L

internal fun getRecipeId(): Long {
    return lastId++
}

class RecipeMemStore : RecipeStore {

    val recipes = ArrayList<RecipeModel>()
    val gson = Gson()

    override fun findAll(): List<RecipeModel> {
        return recipes
    }

    override fun create(recipe: RecipeModel) {
        recipe.id = getRecipeId()
        recipes.add(recipe)
        logAll()
        i("created")
    }

    override fun update(recipe: RecipeModel) {
        val foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            foundRecipe.title = recipe.title
            foundRecipe.description = recipe.description
            foundRecipe.instructions = recipe.instructions
            foundRecipe.image = recipe.image
            logAll()
            i("updated")
        }

    }
    override fun delete(recipe: RecipeModel){
        val foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null){
            recipes.remove(foundRecipe)
        }
    }

    override fun ingredients(recipe: RecipeModel): ArrayList<IngredientModel>? {
        TODO("Not yet implemented")
    }

    fun logAll() {
        recipes.forEach{ i("${it}") }
    }

    fun saveDataToJson(context: Context){
        val jsonString = gson.toJson(recipes)

        try {
            val fileOutputStream = context.openFileOutput("recipes.json", Context.MODE_PRIVATE)
            fileOutputStream.write(jsonString.toByteArray())
            fileOutputStream.close()
            i("creating / writing the json succeeded")
        } catch (e: Exception) {
            i("creating / writing the json failed")
        }
        val fileInputStream = context.openFileInput("recipes.json")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val jsonString1 = bufferedReader.readText()
        //val recipes: ArrayList<RecipeModel> = gson.fromJson(jsonString, object : TypeToken<ArrayList<RecipeModel>>() {}.type)
        i(jsonString1)
        // for (recipe in recipes) {
        //    i(recipe.toString())
        //}



/*

        // Create a JSONObject from the recipe object
        val array = JSONArray()
        val jsonRecipe = JSONObject()

        for (recipe: RecipeModel in recipes){
            jsonRecipe.put("id", recipe.id)
            jsonRecipe.put("title", recipe.title)
            jsonRecipe.put("description", recipe.description)
            jsonRecipe.put("instructions", recipe.instructions)
            array.put(`jsonRecipe`)
        }

        // Write the JSONObject to a file
       */
        /*val file = File(filesDir, "recipe.json")
        file.writeText(array.toString())

        i("JSON data written to file: ${jsonRecipe.toString()}")

        val filename = "recipe.json"
        val fileContents = applicationContext.openFileInput(filename).bufferedReader().use { it.readText() }
        i("File contents: $fileContents")
        //i("ArrayList contents: $recipes")*/
    }



}


