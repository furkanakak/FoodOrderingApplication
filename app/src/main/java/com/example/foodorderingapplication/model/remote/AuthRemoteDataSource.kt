package com.example.foodorderingapplication.model.remote

import com.example.foodorderingapplication.model.entity.mealadd.MealAddRequest
import com.example.foodorderingapplication.model.entity.order.OrderAddRequest
import com.example.foodorderingapplication.model.entity.profile.UserRequest
import com.example.foodorderingapplication.model.entity.restaurantadd.RestaurantAddRequest
import com.example.foodorderingapplication.utils.BaseDataSource
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val authAPIService: AuthAPIService) :
    BaseDataSource() {
    suspend fun postRestaurant(request: RestaurantAddRequest) = getResult {
        authAPIService.postRestaurant(request)
    }

    suspend fun postMeal(restaurantId: String, request: MealAddRequest) = getResult {
        authAPIService.postMeal(restaurantId, request)
    }

    suspend fun getOrders() = getResult { authAPIService.getOrders() }

    suspend fun updateUser(request: UserRequest) = getResult { authAPIService.updateUser(request) }

    suspend fun getUser() = getResult { authAPIService.getUser() }

    suspend fun postOrder(orderAddRequest: OrderAddRequest) = getResult {
        authAPIService.postOrder(orderAddRequest)
    }
}