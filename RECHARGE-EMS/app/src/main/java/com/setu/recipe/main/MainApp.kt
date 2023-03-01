package com.setu.recipe.main

import android.app.Application
import com.setu.recipe.models.RecipeMemStore
import com.setu.recipe.models.RecipeModel
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val recipe = ArrayList<RecipeModel>()
    val recipes = RecipeMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Recipe started")


        recipes.recipes.add(RecipeModel("Maximilian Grimm", 1, "Leberkaese","Leber kaese", "Dat schmeckt jedermann"))
        recipes.recipes.add(RecipeModel("Maximilian Grimm", 2, "Leberkaese","Leber kaese", "Dat schmeckt jedermann"))
        recipes.recipes.add(RecipeModel("Maximilian Grimm", 3, "Leberkaese","Leber kaese", "Dat schmeckt jedermann"))
        //recipe.add(PlacemarkModel("Two", "About two..."))
        //recipe.add(PlacemarkModel("Three", "About three..."))
    }
}
