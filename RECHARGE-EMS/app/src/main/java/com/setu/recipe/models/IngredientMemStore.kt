package com.setu.recipe.models

import android.content.Context
import com.google.gson.Gson
import timber.log.Timber.i
import java.io.BufferedReader
import java.io.InputStreamReader


private var lastId = 0L

internal fun getIngredientId(): Long {
    return lastId++
}

class IngredientMemStore : IngredientStore {

    val ingredients = ArrayList<IngredientModel>()
    val gson = Gson()

    override fun findAll(): List<IngredientModel> {
        return ingredients
    }

    override fun create(ingredient: IngredientModel) {
        ingredient.id = getIngredientId()
        ingredients.add(ingredient)
        logAll();
        i("created")
    }


    override fun delete(ingredient: IngredientModel){
        var foundIngredient: IngredientModel? = ingredients.find { p -> p.id == ingredient.id }
        if (foundIngredient != null){
            ingredients.remove(foundIngredient)
        }
    }

    fun logAll() {
        ingredients.forEach{ i("${it}") }
    }

    fun saveDataToJson(context: Context) {
        val jsonString = gson.toJson(ingredients)

        try {
            val fileOutputStream = context.openFileOutput("ingredients.json", Context.MODE_PRIVATE)
            fileOutputStream.write(jsonString.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            i(e)
        }
        val fileInputStream = context.openFileInput("recipes.json")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val jsonString1 = bufferedReader.readText()
        //val recipes: ArrayList<IngredientModel> = gson.fromJson(jsonString, object : TypeToken<ArrayList<IngredientModel>>() {}.type)
        i(jsonString1)
        // for (recipe in recipes) {
        //    i(recipe.toString())
        //}


    }

}


