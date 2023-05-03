package com.setu.recipe.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeListBinding
import com.setu.recipe.main.MainApp
import com.setu.recipe.models.RecipeModel

class RecipeListActivity : AppCompatActivity(),  RecipeListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRecipeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        //binding.toolbar.setCollapseIcon(R.drawable.ic_launcher_foreground)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RecipeAdapter(app.recipes.findAll(), this)

        // binding.recyclerView.adapter.notifyDataSetChanged()
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.itemAnimator = null
       // binding.recyclerView.adapter.notifyDataSetChanged()
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        //binding.recyclerView.itemAnimator = null



    }

    //ItemTouchHelper.
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RecipeActivity::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.item_navicon -> {
                val launcherIntent = Intent(this, NavigationActivity::class.java)
                getResult.launch(launcherIntent)

                finish()

            }
            R.id.item_search -> {
                val launcherIntent = Intent(this, RecipeListSearchActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.recipes.findAll().size)
            }
        }

    override fun onRecipeClick(recipe: RecipeModel) {
        val launcherIntent = Intent(this, RecipeActivity::class.java)
        launcherIntent.putExtra("recipe_edit", recipe)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.recipes.findAll().size)
                (binding.recyclerView.adapter)?.notifyDataSetChanged()
            }
        }

}

