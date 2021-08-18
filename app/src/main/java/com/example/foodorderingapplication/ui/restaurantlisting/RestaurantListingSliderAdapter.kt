package com.example.foodorderingapplication.ui.restaurantlisting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapplication.R



class RestaurantListingSliderAdapter(val context : Context) : RecyclerView.Adapter<RestaurantListingSliderAdapter.MyViewHolder>() {

    lateinit var list : List<Int>

    fun setContentList(list: List<Int>)
    {
        this.list = list
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var image = itemView.findViewById<ImageView>(R.id.slider_item_imageview)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.image.setImageResource(list[position])
    }

    override fun getItemCount(): Int = list.size


}