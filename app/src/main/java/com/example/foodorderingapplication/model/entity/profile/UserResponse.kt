package com.example.foodorderingapplication.model.entity.profile

import com.example.foodorderingapplication.model.entity.User
import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("data")
    val user: User,
    @SerializedName("success")
    val success: Boolean
)