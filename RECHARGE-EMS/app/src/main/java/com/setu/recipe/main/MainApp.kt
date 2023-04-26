package com.setu.recipe.main

import android.app.Application
import android.net.Uri
import com.google.gson.Gson
import com.setu.recipe.models.*
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import timber.log.Timber.i
import java.io.InputStream

class MainApp : Application() {

    //val recipe = ArrayList<RecipeModel>()
    lateinit var recipes: RecipeStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Recipe started")

        //recipes = RecipeMemStore()
        recipes = RecipeJSONStore(applicationContext)
        //recipes.recipes.add(RecipeModel("Maximilian Grimm", 1, "Burger","Healthy Burger Recipe", "First you need to prepare the salad. Use...", a))

        /*

        // ,
        // , //com.android.providers.downloads.documents/document/msf%3A1000000041
        // , //com.android.providers.downloads.documents/document/msf%3A1000000038
        val a = Uri.parse("//com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fhamburger-494706__340.jpg")
        //recipes.recipes.add(RecipeModel("Maximilian Grimm", 2, "Pasta","Pasta with Pesto and vegetables", "First you need to take a pot and use 1L of Water ...", a))
        //recipes.recipes.add(RecipeModel("Maximilian Grimm", 3, "Salmon","Fried Salmnon w vegetables", "First you need to cut and prepare the vegetables for... "))


        val filename = "recipes.json"
        try {
            /*val fileContents = openFileInput(filename).bufferedReader().useLines { lines ->
                lines.fold("") { some, text ->
                    "$some\n$text"
                }
            }*/

            val inputStream:InputStream = assets.open("RecipeData.json")
            val json = inputStream.bufferedReader().use{it.readText()}
            //val json = Gson().fromJson(fileContents, Array<RecipeModel>::class.java).toList()
            for (rec : RecipeModel in Gson().fromJson(json, Array<RecipeModel>::class.java).toList()){
                recipes.recipes.add(rec)
            }
            i("reading the json succeeded")
        } catch (e : Exception){
            i("reading the json failed")
        }
        */


    }
}
