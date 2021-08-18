package com.example.foodorderingapplication.ui.restaurantlisting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.category.Category


class CategoryAdapter(val context : Context) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    lateinit var list : List<Category>

    class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var categoryPic = itemView.findViewById<ImageView>(R.id.categoryItemImageView)
        var categoryName = itemView.findViewById<TextView>(R.id.categoryItemTextView)
        var layout = itemView.findViewById<ConstraintLayout>(R.id.categoryItemLayout)
    }


    fun setContentList(list: List<Category>)
    {
        this.list = list
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.setText(list[position].title)
        var picUrl = ""
        when(position)
        {
            0 ->{
                picUrl ="cat_1"
                holder.layout.setBackgroundResource(R.drawable.category_background)

            }
            1 ->{
                picUrl ="cat_2"
                holder.layout.setBackgroundResource(R.drawable.category_background2)

            }
            2->{
                picUrl ="cat_3"
                holder.layout.setBackgroundResource(R.drawable.category_background3)

            }
            3 ->{
                picUrl ="cat_4"
                holder.layout.setBackgroundResource(R.drawable.category_background4)

            }
            4 ->{
                picUrl ="cat_5"
                holder.layout.setBackgroundResource(R.drawable.category_background5)

            }
        }

        holder.categoryPic.setImageResource(list[position].pic)

    }

    override fun getItemCount(): Int = list.size


}