package com.setu.recipe.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.setu.recipe.databinding.CardIngredientBinding
import com.setu.recipe.models.IngredientModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i


interface IngredientListener {
    fun onRecipeClick(ingredient: IngredientModel)
}

class IngredientAdapter constructor(private var ingredients: ArrayList<IngredientModel>, private val listener: IngredientListener) :
        RecyclerView.Adapter<IngredientAdapter.MainHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
            val binding = CardIngredientBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return MainHolder(binding)
        }

        override fun onBindViewHolder(holder: MainHolder, position: Int) {
            val ingredient = ingredients[holder.adapterPosition]
            holder.bind(ingredient, listener)
        }

        override fun getItemCount(): Int = ingredients.size

        class MainHolder(private val binding : CardIngredientBinding) :
            RecyclerView.ViewHolder(binding.root) {

            // nur für die Card im recycleview
            fun bind(ingredient: IngredientModel, listener: IngredientListener) {
                binding.ingredientNameShow.text = ingredient.name.toString()
                binding.ingredientAmountShow.text = ingredient.amount.toString()
                binding.ingredientUnitShow.text = ingredient.unit.toString()
                /*binding.user.text = "Maxmilian Grimm"
                binding.recipeTitle.text = recipe.title
                binding.description.text = recipe.description
                binding.instructions.text = recipe.instructions*/
                //Picasso.get().load(recipe.image).into(binding.recipeImage2)
                //i(binding.recipeImage2.toString())
                binding.root.setOnClickListener { listener.onRecipeClick(ingredient) }
            }
        }
    }


