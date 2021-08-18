package com.example.foodorderingapplication.model.entity.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val registerData: RegisterData,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("token")
    val token: String
)