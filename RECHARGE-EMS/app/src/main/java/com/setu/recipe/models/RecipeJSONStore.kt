package com.setu.recipe.models

import android.content.Context
import android.net.Uri
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.setu.recipe.helpers.*
import timber.log.Timber
import timber.log.Timber.i
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "recipes2.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<RecipeModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class RecipeJSONStore(private val context: Context) : RecipeStore {

    var recipes = mutableListOf<RecipeModel>()
    val database = Firebase.database
    val myRef = database.getReference("Recipes")

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<RecipeModel> {
        logAll()
        return recipes
    }

    override fun create(recipe: RecipeModel) {
        recipe.id = generateRandomId()
        recipes.add(recipe)
        // Firebase call to create / update recipe

        myRef.child(recipe.id.toString()).setValue(recipe)
        serialize()
    }


    override fun update(recipe: RecipeModel) {
        val foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            foundRecipe.title = recipe.title
            foundRecipe.description = recipe.description
            foundRecipe.instructions = recipe.instructions
            foundRecipe.image = recipe.image
            foundRecipe.ingredients.clear()
            foundRecipe.ingredients.addAll(recipe.ingredients)
            logAll()
            myRef.child(recipe.id.toString()).setValue(recipe)
            Timber.i("updated")
            serialize()
        }
    }

    override fun delete(recipe: RecipeModel) {
        val foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null){
            recipes.remove(foundRecipe)
        }
        Timber.i("updated")
        serialize()
    }

    override fun ingredients(recipe: RecipeModel): ArrayList<IngredientModel>? {
        i("Recipe is: %s", recipe)
        val foundRecipe: RecipeModel? = recipes.find { p -> p.id == recipe.id }
        if (foundRecipe != null) {
            i("found ingredient" + foundRecipe.ingredients)
            return foundRecipe.ingredients
        }else {
            i("Did not find ingredient")
        }

        return null
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(recipes, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        recipes = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        recipes.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
