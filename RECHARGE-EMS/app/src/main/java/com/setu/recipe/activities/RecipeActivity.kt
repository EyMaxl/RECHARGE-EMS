package com.setu.recipe.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.reflect.TypeToken
import com.setu.recipe.R
import com.setu.recipe.databinding.ActivityRecipeBinding
import com.setu.recipe.helpers.showImagePicker
import com.setu.recipe.main.MainApp
import com.setu.recipe.models.*
import com.squareup.picasso.Picasso
import okhttp3.*
import timber.log.Timber.i
import java.io.IOException


class RecipeActivity : AppCompatActivity() , IngredientListener{

    private lateinit var binding: ActivityRecipeBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var recipe = RecipeModel()
    var ingredients = ArrayList<IngredientModel>()
    lateinit var app: MainApp
    val items = arrayOf<String>("Gram", "Milliliter", "Stk.")
    private lateinit var database : DatabaseReference
    private val client = OkHttpClient()


    @SuppressLint("ClickableViewAccessibility", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var edit = false
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp


        val database = Firebase.database
        val myRef = database.getReference("Recipes")

        val layoutManager = LinearLayoutManager(this)



        binding.btnDelete.setVisibility(View.INVISIBLE)
        findViewById<CardView>(R.id.nutritionCard).visibility = View.GONE
        binding.recyclerViewIng.layoutManager = layoutManager
        binding.recyclerViewIng.adapter = IngredientAdapter(ingredients , this)


        if (intent.hasExtra("recipe_edit")) {
            edit = true
            recipe = intent.extras?.getParcelable("recipe_edit")!!
            binding.recipeTitle.setText(recipe.title)
            binding.description.setText(recipe.description)
            binding.instruction.setText(recipe.instructions)
            binding.btnDelete.setVisibility(View.VISIBLE)
            binding.btnAdd.setText(R.string.save_recipe)

            if(recipe.nutritions != null){
                findViewById<CardView>(R.id.nutritionCard).visibility = View.VISIBLE
                binding.nutritionCard.nutritionCarb.setText(recipe.nutritions.carbohydrates_total_g.toString())
                binding.nutritionCard.nutritionFat.setText(recipe.nutritions.fat_total_g.toString())
                binding.nutritionCard.nutritionProtein.setText(recipe.nutritions.protein_g.toString())
            }



            ingredients.clear()
            ingredients.addAll(app.recipes.ingredients(recipe)!!)


            if (recipe.image.toUri() != Uri.EMPTY) {
                binding.chooseImage.setText(com.setu.recipe.R.string.change_recipe_image)
            }
            Picasso.get()
                .load(recipe.image.toUri())
                .into(binding.recipeImage)

        }

        //


        /*binding.btnAdd.setOnClickListener() {
           i("add Button Pressed")
        }*/
        //Alternitive binding
        binding.btnAdd.setOnClickListener() {

            var user = Firebase.auth.currentUser
            if (user != null) {
                recipe.user = user.email.toString()
            } else {
                recipe.user = "unknown"
            }

            recipe.title = binding.recipeTitle.text.toString()
            recipe.description = binding.description.text.toString()
            recipe.instructions = binding.instruction.text.toString()
            recipe.ingredients.clear()
            recipe.ingredients.addAll(ingredients)


            // Write a message to the database
            recipe.image = recipe.image.toString()

            var allIngredients : String = ""
            var ingredientcount = 0
            recipe.ingredients.forEach{
                allIngredients = allIngredients + ingredients.get(ingredientcount).name
                allIngredients = allIngredients + " " + ingredients.get(ingredientcount).amount
                allIngredients = allIngredients + ingredients.get(ingredientcount).unit + ", "
                ingredientcount ++
            }
            i("All Ingredients: " + allIngredients)
            val url = "https://api.api-ninjas.com/v1/nutrition?query=" // 1lb brisket and fries
            val urlWithIngredients = url + allIngredients
            val request = Request.Builder()
                .url(urlWithIngredients)
                .addHeader("X-Api-Key","Nk4oguJbPRiBtr6MKZpkfA==QRjaMjza4pzDM99j")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {}
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body()?.string()
                    println(responseBody)
                    val nutritiType = object : TypeToken<List<NutritionModel>>() {}.type
                    val nutritionResponse : List<NutritionModel> = gsonBuilder.fromJson(responseBody, nutritiType)
                    println(nutritionResponse)
                    var fats = 0.0
                    var carbs = 0.0
                    var proteins = 0.0
                    for(n:NutritionModel in nutritionResponse){
                        fats = fats + n.fat_total_g
                        carbs = carbs + n.carbohydrates_total_g
                        proteins = proteins + n.protein_g
                    }
                    recipe.nutritions.fat_total_g = fats
                    recipe.nutritions.carbohydrates_total_g = carbs
                    recipe.nutritions.protein_g = proteins
                    println("Nutritional values stored are:\n" + "carbs: " + carbs + ", fats: " + fats + ", protein: " + proteins)
                }
            })



            if (recipe.title.isNotEmpty()) {
                if (edit) {
                    app.recipes.update(recipe.copy())
                } else {
                    app.recipes.create(recipe.copy())
                }

                i("add Button Pressed: $recipe")

                //saveDataToJson()
                //app.recipes.saveDataToJson(this)


                setResult(RESULT_OK)
                finish()
            }

            Snackbar
                .make(it, R.string.enter_Title, Snackbar.LENGTH_LONG)
                .show()
        }

        binding.addIngredient.btnAddIng.setOnClickListener(){
            var tmpIngredient = IngredientModel()
            tmpIngredient.name = binding.addIngredient.ingredientName.text.toString()
            tmpIngredient.amount = binding.addIngredient.amountIng.value.toLong()
            tmpIngredient.unit = binding.addIngredient.unitIng.text.toString()
            if (tmpIngredient.name.isNotEmpty()) {
                ingredients.add(tmpIngredient)
                binding.addIngredient.ingredientName.text.clear()
                binding.addIngredient.amountIng.value = 0
                //binding.recyclerViewIng.
                i("added ingredient: " + ingredients)
                (binding.recyclerViewIng.adapter)?.
                notifyItemRangeChanged(0, ingredients.size)
                (binding.recyclerViewIng.adapter)?.notifyDataSetChanged()
            } else {
                Snackbar
                    .make(it, R.string.enter_Title, Snackbar.LENGTH_LONG)
                    .show()
            }

        }

        binding.btnDelete.setOnClickListener() {
            app.recipes.delete(recipe.copy())
//<<<<<<< HEAD
            myRef.child(recipe.copy().title).removeValue()
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher,this)
        }
        registerImagePickerCallback()

        val adapterItem1 = ArrayAdapter<String>(this,R.layout.dropdownlistitem,items )
        binding.addIngredient.unitIng.setAdapter(adapterItem1)
        binding.addIngredient.unitIng.setText(adapterItem1.getItem(0),false)
        binding.addIngredient.unitIng.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                //... your stuff
                val item = parent.getItemAtPosition(position).toString()
                Toast.makeText(applicationContext, item,Toast.LENGTH_SHORT).show()
            }
        binding.addIngredient.amountIng.minValue = 1
        binding.addIngredient.amountIng.maxValue = 1000
        //binding.addIngredient.editTextNumber.displayedValues = 1

        binding.addIngredient.amountIng.setOnValueChangedListener { picker, oldVal, newVal ->
            picker.value = (newVal / 10) * 10 // set the value to the nearest multiple of 10
        }

        binding.addIngredient.amountIng.setOnLongClickListener {
            val currentValue = binding.addIngredient.amountIng.value
            binding.addIngredient.amountIng.value = currentValue + 100 // increment by 100
            true
        }

        binding.addIngredient.amountIng.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val currentValue = binding.addIngredient.amountIng.value
                binding.addIngredient.amountIng.value = currentValue + 10 // increment by 10
            }
            false
        }
/*=======
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
>>>>>>> 3efde142d93d5bd0351ac062759fa8ab7dbaa32f*/

    }


    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            recipe.image = image.toString()

                            Picasso.get()
                                .load(recipe.image.toUri())
                                .into(binding.recipeImage)
                            binding.chooseImage.setText(R.string.change_recipe_image)

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

    override fun onRecipeClick(ingredient: IngredientModel) {
        val dialogBinding = layoutInflater.inflate(R.layout.ingredient_dialog, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)

        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()

        val deletButton = dialogBinding.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.alertIng_delete)
        deletButton.setOnClickListener {

            val foundIngredient: IngredientModel? = recipe.ingredients.find { p -> p.id == ingredient.id }
            if (foundIngredient != null){
                ingredients.remove(foundIngredient)
                i("deleted Ingredient! %s", foundIngredient)
            }


            (binding.recyclerViewIng.adapter)?.
            notifyItemRangeChanged(0,ingredients.size)
            (binding.recyclerViewIng.adapter)?.notifyDataSetChanged()

            myDialog.dismiss()
        }

        //getClickResult.launch(launcherIntent)

        /*if (it.resultCode == Activity.RESULT_OK) {
            (binding.recyclerViewIng.adapter)?.
            notifyItemRangeChanged(0,ingredients.size)
            (binding.recyclerViewIng.adapter)?.notifyDataSetChanged()
        }*/
    }




}