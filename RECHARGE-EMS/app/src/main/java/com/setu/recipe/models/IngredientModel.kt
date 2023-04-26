package com.setu.recipe.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//var image: Uri = Uri.EMPTY

@Parcelize
data class IngredientModel(
    var id: Long = 0,
    var name: String = "",
    var unit: String = "",
    var amount: Long = 0) : Parcelable



interface IngredientStore {
    fun findAll(): List<IngredientModel>
    fun create(ingredient: IngredientModel)
    fun delete(ingredient: IngredientModel)
}