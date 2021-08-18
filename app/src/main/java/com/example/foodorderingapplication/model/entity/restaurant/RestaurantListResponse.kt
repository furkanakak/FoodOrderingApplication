package com.example.foodorderingapplication.model.entity.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantListResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("data")
    val restaurantList: List<Restaurant>,
    @SerializedName("success")
    val success: Boolean
)