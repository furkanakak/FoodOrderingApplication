package com.example.foodorderingapplication.ui.mealadd

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.databinding.FragmentMealAddBinding
import com.example.foodorderingapplication.model.entity.Ingredient
import com.example.foodorderingapplication.utils.Resource
import com.example.foodorderingapplication.utils.afterTextChanged
import com.example.foodorderingapplication.utils.gone
import com.example.foodorderingapplication.utils.show
import com.google.android.flexbox.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealAddFragment : Fragment() {
    private val args: MealAddFragmentArgs by navArgs()
    private lateinit var binding: FragmentMealAddBinding
    private val viewModel: MealAddViewModel by viewModels()
    private lateinit var ingredientsList: MutableList<Ingredient>
    private lateinit var ingredientAdapter: IngredientRecyclerViewAdapter
    private lateinit var layoutManager: FlexboxLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setHomeAsUpIndicator(R.drawable.ic_back)
            setDisplayHomeAsUpEnabled(true)
        }
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
            @SuppressLint("NotifyDataSetChanged")
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
        if (hasEmptyFields())
            return
        val ingredients: MutableList<String> = mutableListOf()
        val name = binding.mealNameEditText.editText?.text.toString()
        val url = binding.mealUrlEditText.editText?.text.toString()
        val price = binding.mealPriceEditText.editText?.text.toString()

        ingredientsList.forEach {
            ingredients.add(it.ingredient)

            viewModel.addMeal(
                args.restaurantId,
                name,
                url,
                price,
                ingredients
            )
                .observe(viewLifecycleOwner) {
                    when (it.status) {
                        Resource.Status.LOADING -> {
                            Log.i(MealAddFragment::class.java.name, it.message.toString())
                            binding.addMealProgressBar.show()
                        }
                        Resource.Status.SUCCESS -> {
                            Log.i(MealAddFragment::class.java.name, it.message.toString())
                            binding.addMealProgressBar.gone()
                            val action =
                                MealAddFragmentDirections.actionMealAddFragmentToRestaurantDetailFragment(
                                    it.data!!.message.restaurant
                                )
                            findNavController().navigate(action)
                        }
                        Resource.Status.ERROR -> {
                            //Error response doesn't have data, restaurant will be null. Return home page.
                            Log.e(MealAddFragment::class.java.name, it.message.toString())
                            binding.addMealProgressBar.gone()
                            Toast.makeText(context, "Error occured!", Toast.LENGTH_LONG).show()
                            val action =
                                MealAddFragmentDirections.actionMealAddFragmentToRestaurantDetailFragment(
                                    it.data!!.message.restaurant
                                )
                            findNavController().navigate(action)
                        }
                    }
                }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMealIngredient() {
        if (!binding.mealIngredientsEditText.text.isNullOrEmpty()) {
            ingredientsList.add(Ingredient(binding.mealIngredientsEditText.text.toString(), true))
            ingredientAdapter.notifyDataSetChanged()
            binding.mealIngredientsEditText.text!!.clear()
        }
    }

    private fun hasEmptyFields(): Boolean {

        if (binding.mealNameEditText.editText?.text.isNullOrEmpty()) {
            binding.mealNameEditText.error = "This can't be empty!"
            return true
        } else {
            binding.mealNameEditText.error = null
        }

        if (binding.mealPriceEditText.editText?.text.isNullOrEmpty()) {
            binding.mealPriceEditText.error = "This can't be empty!"
            return true
        } else {
            binding.mealPriceEditText.error = null
        }

        if (binding.mealDescriptionLayout.editText?.text.isNullOrEmpty()) {
            binding.mealDescriptionLayout.error = "This can't be empty!"
            return true
        } else {
            binding.mealDescriptionLayout.error = null
        }

        if (ingredientsList.size <= 0) {
            binding.errorTextView.visibility = View.VISIBLE
            binding.errorTextView.text = getString(R.string.ingredients_error)
            return true
        } else {
            binding.errorTextView.visibility = View.GONE
        }
        return false
    }
}

