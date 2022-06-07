package com.example.foodorderingapplication.ui.restaurantdetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foodorderingapplication.model.entity.restaurant.Restaurant
import com.example.foodorderingapplication.ui.meal.MealListFragment


private const val FRAGMENT_COUNT = 2

class RestaurantDetailViewPagerAdapter(activity: FragmentActivity, val restaurant: Restaurant) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = FRAGMENT_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RestaurantDetailSectionFragment(restaurant)
            1 -> MealListFragment(restaurant)
            else -> RestaurantDetailSectionFragment(restaurant)
        }
    }
}