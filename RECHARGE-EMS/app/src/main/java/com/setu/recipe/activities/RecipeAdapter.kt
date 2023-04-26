package com.setu.recipe.activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.setu.recipe.databinding.CardRecipeBinding
import com.setu.recipe.models.RecipeModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i


interface RecipeListener {
    fun onRecipeClick(recipe: RecipeModel)
}

class RecipeAdapter constructor(private var recipes: List<RecipeModel>,  private val listener: RecipeListener) :
    RecyclerView.Adapter<RecipeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardRecipeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val recipe = recipes[holder.adapterPosition]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int = recipes.size

    class MainHolder(private val binding : CardRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: RecipeModel, listener: RecipeListener) {
            binding.user.text = recipe.user
            binding.recipeTitle.text = recipe.title
            binding.description.text = recipe.description
            binding.instructions.text = recipe.instructions
            Picasso.get().load(recipe.image.toUri()).into(binding.recipeImage2)
            i(binding.recipeImage2.toString())
            binding.root.setOnClickListener { listener.onRecipeClick(recipe) }
        }
    }
}


