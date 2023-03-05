package com.setu.recipe.main

import android.app.Application
import android.net.Uri
import com.setu.recipe.models.RecipeMemStore
import com.setu.recipe.models.RecipeModel
import com.setu.recipe.models.image
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
        // ,
        // , //com.android.providers.downloads.documents/document/msf%3A1000000041
        // , //com.android.providers.downloads.documents/document/msf%3A1000000038
        val a = Uri.parse("//com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fhamburger-494706__340.jpg")
        recipes.recipes.add(RecipeModel("Maximilian Grimm", 1, "Burger","Healthy Burger Recipe", "First you need to prepare the salad. Use...", a))
        recipes.recipes.add(RecipeModel("Maximilian Grimm", 2, "Pasta","Pasta with Pesto and vegetables", "First you need to take a pot and use 1L of Water ...", a))
        recipes.recipes.add(RecipeModel("Maximilian Grimm", 3, "Salmon","Fried Salmnon w vegetables", "First you need to cut and prepare the vegetables for... "))

    }
}
