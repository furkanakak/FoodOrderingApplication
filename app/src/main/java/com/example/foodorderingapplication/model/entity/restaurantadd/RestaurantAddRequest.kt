package com.example.foodorderingapplication.model.entity.restaurantadd

import com.google.gson.annotations.SerializedName

data class RestaurantAddRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("deliveryInfo")
    val deliveryInfo: String,
    @SerializedName("deliveryTime")
    val deliveryTime: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("minDeliveryFee")
    val minDeliveryFee: String,
    @SerializedName("paymentMethods")
    val paymentMethods: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("website")
    val website: String
)
