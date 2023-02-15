package com.setu.placemark.main

import android.app.Application
import com.setu.placemark.models.RecipeModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val recipe = ArrayList<RecipeModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        //recipe.add(PlacemarkModel("One", "About one..."))
        //recipe.add(PlacemarkModel("Two", "About two..."))
        //recipe.add(PlacemarkModel("Three", "About three..."))
    }
}
