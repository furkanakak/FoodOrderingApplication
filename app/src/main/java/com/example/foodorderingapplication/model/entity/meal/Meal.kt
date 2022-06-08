package com.example.foodorderingapplication.model.entity.meal

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("_id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("imageUrl")
    val image: String,
    @SerializedName("ingredients")
    val ingredients: ArrayList<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String
)