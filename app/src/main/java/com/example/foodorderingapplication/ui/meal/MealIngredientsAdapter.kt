package com.example.foodorderingapplication.ui.meal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R

class MealIngredientsAdapter  : RecyclerView.Adapter<MealIngredientsAdapter.MealIngredientsViewHolder>() {

    private var ingredients = ArrayList<String>()

    class MealIngredientsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientTextView: TextView = view.findViewById(R.id.ingredientTextView)

        fun setIngredient(ingredient: String) {
            ingredientTextView.text = ingredient
        }

    }
    fun setIngredients(ingredientList: ArrayList<String>) {
        this.ingredients = ingredientList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealIngredientsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_check_item, parent, false)
        return MealIngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealIngredientsViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.setIngredient(ingredient)
    }

    override fun getItemCount(): Int = ingredients.size
}