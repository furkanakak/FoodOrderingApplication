package com.example.foodorderingapplication.model.entity.category

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("title")
    val title: String,
    @SerializedName("image")
    val pic: Int
)
