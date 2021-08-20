package com.example.foodorderingapplication.ui.mealadd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentMealAddBinding
import com.example.foodorderingapplication.model.entity.Ingredient
import com.example.foodorderingapplication.utils.afterTextChanged
import com.google.android.flexbox.*

class MealAddFragment : Fragment() {
    private val args: MealAddFragmentArgs by navArgs()
    private lateinit var binding: FragmentMealAddBinding
    private lateinit var ingredientsList : MutableList<Ingredient>
    private lateinit var ingredientAdapter : IngredientRecyclerViewAdapter
    private lateinit var layoutManager : FlexboxLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        (activity as AppCompatActivity).supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }
        Glide.with(requireContext())
            .load("https://firebasestorage.googleapis.com/v0/b/fooddeliveryapp-fe5bf.appspot.com/o/images%2Fpizza.jpg?alt=media&token=7ffc6831-d9ae-4e9a-96a9-7898bc546878")
            .into(binding.addMealImageView)

        initializeRecyclerView()
        addListeners()

    }
    private fun initializeRecyclerView() {
        ingredientsList = mutableListOf()
        ingredientAdapter = IngredientRecyclerViewAdapter(ingredientsList)

        layoutManager = FlexboxLayoutManager(activity)
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.alignItems = AlignItems.FLEX_START

        ingredientAdapter.addListener(object : IngredientRecyclerViewAdapterListener {
            override fun onIngredientClickListener(ingredient: Ingredient, position: Int) {
                ingredientsList.removeAt(position)
                ingredientAdapter.notifyDataSetChanged()
            }
        })

        binding.mealRecyclerView.layoutManager = layoutManager
        binding.mealRecyclerView.adapter = ingredientAdapter
    }
    private fun addListeners() {
        binding.mealNameEditText.editText?.afterTextChanged(binding.mealNameEditText)
        binding.mealPriceEditText.editText?.afterTextChanged(binding.mealPriceEditText)
        binding.mealDescriptionLayout.editText?.afterTextChanged(binding.mealDescriptionLayout)

        binding.addMealIngredientLogo.setOnClickListener {
            addMealIngredient()
        }

        binding.addMealButton.setOnClickListener {
            addMeal()
        }
    }

    private fun addMeal() {
        if(hasEmptyFields())
            return
        val ingredients: MutableList<String> = mutableListOf()
        val name = binding.mealNameEditText.editText?.text.toString()
        val price = binding.mealPriceEditText.editText?.text.toString()

        ingredientsList.forEach {
            ingredients.add(it.ingredient)
        }
    }

    private fun addMealIngredient() {
        if(!binding.mealIngredientsEditText.text.isNullOrEmpty()){
            ingredientsList.add(Ingredient(binding.mealIngredientsEditText.text.toString(), true))
            ingredientAdapter.notifyDataSetChanged()
            binding.mealIngredientsEditText.text!!.clear()
        }
    }

    private fun hasEmptyFields() : Boolean {

        if(binding.mealNameEditText.editText?.text.isNullOrEmpty()){
            binding.mealNameEditText.error = "This can't be empty!"
            return true
        } else {
            binding.mealNameEditText.error = null
        }

        if(binding.mealPriceEditText.editText?.text.isNullOrEmpty()){
            binding.mealPriceEditText.error = "This can't be empty!"
            return true
        } else {
            binding.mealPriceEditText.error = null
        }

        if(binding.mealDescriptionLayout.editText?.text.isNullOrEmpty()){
            binding.mealDescriptionLayout.error = "This can't be empty!"
            return true
        } else {
            binding.mealDescriptionLayout.error = null
        }

        if(ingredientsList.size <= 0){
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = getString(R.string.ingredients_error)
            return true
        } else {
            binding.errorTextView.visibility = View.GONE
        }

        return false
    }







    }

