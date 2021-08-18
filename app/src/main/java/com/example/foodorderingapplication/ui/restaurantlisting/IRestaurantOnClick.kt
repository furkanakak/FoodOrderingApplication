package com.example.foodorderingapplication.ui.restaurantlisting

import com.example.foodorderingapplication.model.entity.restaurant.Restaurant

interface IRestaurantOnClick {
    fun onClick(restaurant: Restaurant)
}