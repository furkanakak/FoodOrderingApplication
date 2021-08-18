package com.example.foodorderingapplication.model.entity.mealadd

import com.google.gson.annotations.SerializedName

data class MealAddRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("ingredients")
    val ingredients: List<String>
)
