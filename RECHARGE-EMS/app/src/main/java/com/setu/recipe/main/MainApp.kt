package com.setu.recipe.main

import android.app.Application
import com.setu.recipe.models.RecipeMemStore
import com.setu.recipe.models.RecipeModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val recipe = ArrayList<RecipeModel>()
    val recipes = RecipeMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        //recipe.add(PlacemarkModel("One", "About one..."))
        //recipe.add(PlacemarkModel("Two", "About two..."))
        //recipe.add(PlacemarkModel("Three", "About three..."))
    }
}
