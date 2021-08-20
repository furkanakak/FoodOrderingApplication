package com.example.foodorderingapplication.ui.mealadd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.Ingredient


class IngredientRecyclerViewAdapter(private val ingredientList : MutableList<Ingredient>)
    : RecyclerView.Adapter<IngredientRecyclerViewAdapter.ModelViewHolder>(){

    private var ingredientClickListener: IngredientRecyclerViewAdapterListener? = null

    class ModelViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val ingredientLayout: CardView = view.findViewById(R.id.ingredientLayout)
        private val ingredientName: TextView = view.findViewById(R.id.ingredientNameTextView)

        fun bindItems(ingredient: Ingredient, position: Int, listener: IngredientRecyclerViewAdapterListener?
        ) {
            ingredientName.text = ingredient.ingredient
            ingredientLayout.setOnClickListener {
                listener?.onIngredientClickListener(ingredient, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_card_item, parent, false)
        return ModelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(ingredientList[position], position, ingredientClickListener)
    }

    override fun getItemCount(): Int = ingredientList.size

    fun addListener(listener: IngredientRecyclerViewAdapterListener) {
        ingredientClickListener = listener
    }


}