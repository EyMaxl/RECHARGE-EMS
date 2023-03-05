package com.setu.recipe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeBinding
import com.setu.recipe.helpers.showImagePicker
import com.setu.recipe.main.MainApp
import com.setu.recipe.models.RecipeModel
import com.squareup.picasso.Picasso
import timber.log.Timber.i


class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var recipe = RecipeModel()
    lateinit var app: MainApp
    val items = arrayOf<String>("Gram", "Milliliter")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp


        binding.btnDelete.setVisibility(View.INVISIBLE)

        if (intent.hasExtra("recipe_edit")) {
            edit = true
            recipe = intent.extras?.getParcelable("recipe_edit")!!
            binding.recipeTitle.setText(recipe.title)
            binding.description.setText(recipe.description)
            binding.instruction.setText(recipe.instructions)
            binding.btnDelete.setVisibility(View.VISIBLE)
            binding.btnAdd.setText(R.string.save_recipe)
            if (recipe.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_recipe_image)
            }
            Picasso.get()
                .load(recipe.image)
                .into(binding.recipeImage)


        }




        /*binding.btnAdd.setOnClickListener() {
           i("add Button Pressed")
        }*/
        //Alternitive binding
        binding.btnAdd.setOnClickListener() {
            recipe.user = "Maximilian Grimm"
            recipe.title = binding.recipeTitle.text.toString()
            recipe.description = binding.description.text.toString()
            recipe.instructions = binding.instruction.text.toString()
            if (recipe.title.isNotEmpty()) {
                if (edit) {
                    app.recipes.update(recipe.copy())
                } else {
                    app.recipes.create(recipe.copy())
                }
                i("add Button Pressed: $recipe")

                //saveDataToJson()
                app.recipes.saveDataToJson(this)

                setResult(RESULT_OK)
                finish()
            }
                Snackbar
                    .make(it, R.string.enter_Title, Snackbar.LENGTH_LONG)
                    .show()

        }

        binding.btnDelete.setOnClickListener() {
            app.recipes.delete(recipe.copy())
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        val adapterItem1 = ArrayAdapter<String>(this,R.layout.dropdownlistitem,items )
        binding.addIngredient.autoCompleteText.setAdapter(adapterItem1)
        binding.addIngredient.autoCompleteText.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                //... your stuff
                val item = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, item,Toast.LENGTH_SHORT).show()
            }
        binding.addIngredient.editTextNumber.minValue = 1
        binding.addIngredient.editTextNumber.maxValue = 1000
        //binding.addIngredient.editTextNumber.displayedValues = 1

    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            recipe.image = result.data!!.data!!
                            Picasso.get()
                                .load(recipe.image)
                                .into(binding.recipeImage)
                                binding.chooseImage.setText(R.string.change_recipe_image)
                            i("Imagename is ${binding.recipeImage.toString()}")
                            binding.recipeImage.getLayoutParams().height = 400;
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


    // Inflater für Cancel button
    override fun onCreateOptionsMenu(cancel: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, cancel)
        return super.onCreateOptionsMenu(cancel)
    }

    // Cancel Button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_OK)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}