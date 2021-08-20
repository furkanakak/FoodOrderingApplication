package com.example.foodorderingapplication.model.remote

import com.example.foodorderingapplication.model.entity.login.LoginRequest
import com.example.foodorderingapplication.model.entity.register.RegisterRequest
import com.example.foodorderingapplication.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: APIService) : BaseDataSource() {

    suspend fun getRestaurants() = getResult { apiService.getRestaurants() }

    suspend fun getRestaurantsByCuisine(cuisine: String) =
        getResult { apiService.getRestaurantsByCuisine(cuisine) }

    suspend fun getRestaurantById(id: String) = getResult { apiService.getRestaurantById(id) }

    suspend fun getMealById(id: String) = getResult { apiService.getMealById(id) }

    suspend fun postLogin(request: LoginRequest) = getResult {
        apiService.login(request)
    }

    suspend fun postRegister(request: RegisterRequest) = getResult {
        apiService.register(request)
    }

}