package com.example.foodorderingapplication.model.entity.restaurant

import com.google.gson.annotations.SerializedName

data class OrderRestaurant(
    @SerializedName("name")
    val name: String
)
