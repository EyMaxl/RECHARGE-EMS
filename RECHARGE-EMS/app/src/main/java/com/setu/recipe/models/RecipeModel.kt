package com.setu.recipe.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.nio.file.Files.list

var image: Uri = Uri.EMPTY


@Parcelize
data class RecipeModel(
    var user: String = "",
    var id: Long = 0,
    var title: String = "",
    var description: String = "",
    var instructions: String = "",
    var image: String = "",
    var ingredients: ArrayList<IngredientModel> = ArrayList<IngredientModel>(),
    var nutritions: NutritionModel = NutritionModel()
) : Parcelable


//var image: Uri = Uri.EMPTY,
// var image: String = "",
interface RecipeStore {
    fun findAll(): List<RecipeModel>
    fun create(recipe: RecipeModel)
    fun update(recipe: RecipeModel)
    fun delete(recipe: RecipeModel)
    fun ingredients(recipe: RecipeModel): ArrayList<IngredientModel>?

}

