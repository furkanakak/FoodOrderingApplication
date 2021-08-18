package com.example.foodorderingapplication.model.entity.mealadd

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("ingredients")
    val ingredients: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("restaurant")
    val restaurant: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("__v")
    val v: Int
)