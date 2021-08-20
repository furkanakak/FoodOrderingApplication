package com.example.foodorderingapplication.ui.mealadd

import com.example.foodorderingapplication.model.entity.Ingredient

interface IngredientRecyclerViewAdapterListener {
    fun onIngredientClickListener(ingredient: Ingredient, position: Int)
}