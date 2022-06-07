package com.example.foodorderingapplication.ui.restaurantlisting

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodorderingapplication.R
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant

class RestaurantListAdapter : RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    private var parentFragment: Fragment? = null

    private lateinit var list: List<Restaurant>

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val containerFrameLayout: FrameLayout =
            view.findViewById(R.id.itemRestaurantContainer)
        private val nameTextView: TextView = view.findViewById(R.id.itemRestaurantNameTextView)
        private val locationTextView: TextView =
            view.findViewById(R.id.itemRestaurantLocationTextView)
        private val imageView: AppCompatImageView = view.findViewById(R.id.itemRestaurantImageView)

        fun bind(restaurant: Restaurant, parentFragment: Fragment?) {
            nameTextView.text = restaurant.name
            locationTextView.text = restaurant.district


            val options = RequestOptions().placeholder(R.mipmap.oops_404_foreground)
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(options)
                .load(restaurant.image).into(imageView)

            containerFrameLayout.setOnClickListener {
                println("adapter")
                println(parentFragment)
                val action =
                    RestaurantListingFragmentDirections.actionRestaurantListingFragmentToRestaurantDetailFragment(
                        restaurant.id
                    )
                parentFragment?.findNavController()?.navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, parentFragment)
    }

    override fun getItemCount(): Int = list.size


    @SuppressLint("NotifyDataSetChanged")
    fun setRestaurantList(list: List<Restaurant>?) {
        list?.let {
            this.list = list
            notifyDataSetChanged()
        }
    }

    fun fragment(parentFragment: Fragment) {
        this.parentFragment = parentFragment
        println(parentFragment)
    }
}