package com.example.foodorderingapplication.ui.meal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.meal.Meal

class MealsListAdapter : RecyclerView.Adapter<MealsListAdapter.MealsListViewHolder>() {
    private var mealList = ArrayList<Meal>()
    private var listener: IMealOnClick? = null

    class MealsListViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private val mealImageView: ImageView = view.findViewById(R.id.mealImageView)
        private val mealTitleTextView: TextView = view.findViewById(R.id.mealTitleTextView)
        private val mealDescriptionTextView: TextView = view.findViewById(R.id.mealDescriptionTextView)
        private val mealPriceTextView: TextView = view.findViewById(R.id.mealPriceTextView)
        private val mealCardView: CardView = view.findViewById(R.id.mealCardView)

        fun setMeal(meal: Meal, listener: IMealOnClick?) {
            val options = RequestOptions().placeholder(R.mipmap.oops_404_foreground)
            Glide.with(mealImageView.context)
                .applyDefaultRequestOptions(options)
                .load(meal.image).into(mealImageView)
            mealTitleTextView.text = meal.name
            mealDescriptionTextView.text = meal.description
            mealPriceTextView.text = meal.price + " $"
            mealCardView.setOnClickListener {
                listener?.onClick(meal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)
        return MealsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealsListViewHolder, position: Int) {
        val meal = mealList[position]
        holder.setMeal(meal, listener)
    }

    override fun getItemCount(): Int = mealList.size

}