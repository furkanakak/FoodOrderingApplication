package com.example.foodorderingapplication.model.entity.restaurant

import com.example.foodorderingapplication.model.entity.meal.Meal
import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("_id")
    val id: String,
    @SerializedName("deliveryInfo")
    val deliveryInfo: String,
    @SerializedName("deliveryTime")
    val deliveryTime: String,
    @SerializedName("imageUrl")
    val image: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("meals")
    val meals: ArrayList<Meal>,
    @SerializedName("minDeliveryFee")
    val minimumDeliveryFee: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("paymentMethods")
    val paymentMethods: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("website")
    val website: String,
    @SerializedName("cuisine")
    val cuisine: String,
)