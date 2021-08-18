package com.example.foodorderingapplication.model.entity

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("about")
    val about: String,
    @SerializedName("blocked")
    val blocked: Boolean,
    @SerializedName("books")
    val books: List<Any>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("job")
    val job: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("orders")
    val orders: List<Any>,
    @SerializedName("place")
    val address: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("profile_image")
    val profileImage: String,
    @SerializedName("resetPasswordExpire")
    val resetPasswordExpire: String,
    @SerializedName("resetPasswordToken")
    val resetPasswordToken: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("__v")
    val v: Int,
    @SerializedName("website")
    val website: String,
    @SerializedName("paymentMethod")
    val paymentMethod: Int
)
