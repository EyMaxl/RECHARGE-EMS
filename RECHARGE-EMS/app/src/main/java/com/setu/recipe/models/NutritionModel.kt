package com.setu.recipe.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NutritionModel(
    var name: String = "",
    var calories: Double = 0.0,
    var serving_size_g: Double = 0.0,
    var fat_total_g: Double = 0.0,
    var fat_saturated_g: Double = 0.0,
    var protein_g: Double = 0.0,
    var sodium_mg: Double = 0.0,
    var potassium_mg: Double = 0.0,
    var cholesterol_mg: Double = 0.0,
    var carbohydrates_total_g: Double = 0.0,
    var fiber_g: Double = 0.0,
    var sugar_g: Double = 0.0) : Parcelable

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

  {"name": "noodles",
  "calories": 161.8,
  "serving_size_g": 100.0,
  "fat_total_g": 0.9,
  "fat_saturated_g": 0.2,
  "protein_g": 5.8,
  "sodium_mg": 0,
  "potassium_mg": 57,
  "cholesterol_mg": 0,
  "carbohydrates_total_g": 31.2,
  "fiber_g": 1.8, "sugar_g": 0.6},
  {"name": "pork", "calories": 236.2, "serving_size_g": 100.0, "fat_total_g": 14.0, "fat_saturated_g": 4.9, "protein_g": 26.2, "sodium_mg": 57, "potassium_mg": 219, "cholesterol_mg": 88, "carbohydrates_total_g": 0.0, "fiber_g": 0.0,
  "sugar_g": 0.0}

 */