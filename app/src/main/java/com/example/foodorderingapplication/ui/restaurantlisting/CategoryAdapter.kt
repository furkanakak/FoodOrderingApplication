package com.example.foodorderingapplication.ui.restaurantlisting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.category.Category


class CategoryAdapter(val context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var listener: IItemOnClick? = null
    lateinit var list: List<Category>

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryPic = itemView.findViewById<ImageView>(R.id.categoryItemImageView)
        var categoryName = itemView.findViewById<TextView>(R.id.categoryItemTextView)
        var layout = itemView.findViewById<ConstraintLayout>(R.id.categoryItemLayout)
        fun bind(category: Category, listener: IItemOnClick?) {
            layout.setOnClickListener {
                listener?.onClick(category)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setContentList(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addListener(listener: IItemOnClick?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.setText(list[position].title)
        var picUrl = ""
        when (position) {
            0 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background)
            }
            1 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background2)
            }
            2 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background3)
            }
            3 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background4)
            }
            4 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background5)
            }
            5 -> {
                holder.layout.setBackgroundResource(R.drawable.category_background)
            }
        }

        holder.categoryPic.setImageResource(list[position].pic)
        val item = list[position]
        holder.bind(item, listener)
    }

    override fun getItemCount(): Int = list.size
}