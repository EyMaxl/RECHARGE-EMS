package com.setu.recipe.activities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeListSearchBinding
import com.setu.recipe.databinding.CardRecipeBinding
import com.setu.recipe.main.MainApp
import com.setu.recipe.models.RecipeModel
import timber.log.Timber.i

class RecipeListSearchActivity : AppCompatActivity(),  RecipeListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRecipeListSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding = ActivityRecipeListSearchBinding.inflate(layoutInflater)
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

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //i(TAG,"Llego al querysubmit")
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //i(TAG,"Llego al querytextchange")
                filterList(newText)
                return true
            }
        })


    }

    fun filterList(text : String){
        val filteredList = ArrayList<RecipeModel>()
        for ( item : RecipeModel in app.recipes.findAll()){ // war ursprünglich recipes.recipes
            if(item.title.toLowerCase().contains((text.toLowerCase()))){
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()){
            setFilteredList(filteredList)
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
        } else {
            setFilteredList(filteredList)
        }
    }

    fun setFilteredList(filteredList : ArrayList<RecipeModel>){
        this.binding.recyclerView.adapter = RecipeAdapter(filteredList, this)
        (binding.recyclerView.adapter)?.notifyDataSetChanged()
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_back, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_back -> {
                finish()
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

