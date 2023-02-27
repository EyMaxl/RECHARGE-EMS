package com.setu.recipe.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeModel(var id: Long = 0, var title: String = "", var description: String = "", var instructions: String = "") :
    Parcelable

interface RecipeStore {
    fun findAll(): List<RecipeModel>
    fun create(recipe: RecipeModel)
    fun update(recipe: RecipeModel)
}

