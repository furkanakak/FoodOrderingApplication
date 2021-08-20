package com.example.foodorderingapplication.model.remote

import com.example.foodorderingapplication.model.entity.login.LoginRequest
import com.example.foodorderingapplication.model.entity.login.LoginResponse
import com.example.foodorderingapplication.model.entity.meal.MealResponse
import com.example.foodorderingapplication.model.entity.register.RegisterRequest
import com.example.foodorderingapplication.model.entity.register.RegisterResponse
import com.example.foodorderingapplication.model.entity.restaurant.RestaurantListResponse
import com.example.foodorderingapplication.model.entity.restaurant.RestaurantResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("a/restaurant")
    suspend fun getRestaurants(): Response<RestaurantListResponse>

    @GET("a/restaurant/cuisine/{cuisineName}")
    suspend fun getRestaurantsByCuisine(@Path("cuisineName") cuisine: String): Response<RestaurantListResponse>

    @GET("a/restaurant/{id}")
    suspend fun getRestaurantById(@Path("id") id: String): Response<RestaurantResponse>

    @GET("a/meal/{id}")
    suspend fun getMealById(@Path("id") id: String): Response<MealResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>


}