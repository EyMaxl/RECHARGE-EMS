package com.setu.recipe.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
data class NutritionModel(
    var name: String = "",
    var calories: Long = 0,
    var serving_size_g: Long = 0,
    var fat_total_g: Long = 0,
    var fat_saturated_g: Long = 0,
    var protein_g: Long = 0,
    var sodium_mg: Long = 0,
    var potassium_mg: Long = 0,
    var cholesterol_mg: Long = 0,
    var carbohydrates_total_g: Long = 0,
    var fiber_g: Long = 0,
    var sugar_g: Long = 0) //: Parcelable

/*  {
    "name": "fries",
    "calories": 317.7,
    "serving_size_g": 100,
    "fat_total_g": 14.8,
    "fat_saturated_g": 2.3,
    "protein_g": 3.4,
    "sodium_mg": 212,
    "potassium_mg": 124,
    "cholesterol_mg": 0,
    "carbohydrates_total_g": 41.1,
    "fiber_g": 3.8,
    "sugar_g": 0.3
  }

 */