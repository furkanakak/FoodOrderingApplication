package com.example.foodorderingapplication.model

import com.example.foodorderingapplication.model.entity.login.LoginRequest
import com.example.foodorderingapplication.model.entity.mealadd.MealAddRequest
import com.example.foodorderingapplication.model.entity.order.OrderAddRequest
import com.example.foodorderingapplication.model.entity.profile.UserRequest
import com.example.foodorderingapplication.model.entity.register.RegisterRequest
import com.example.foodorderingapplication.model.entity.restaurantadd.RestaurantAddRequest
import com.example.foodorderingapplication.model.local.LocalDataSource
import com.example.foodorderingapplication.model.remote.AuthRemoteDataSource
import com.example.foodorderingapplication.model.remote.RemoteDataSource
import com.example.foodorderingapplication.utils.performAuthTokenNetworkOperation
import com.example.foodorderingapplication.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
    private var authRemoteDataSource: AuthRemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    fun login(request: LoginRequest) = performAuthTokenNetworkOperation(
        call = {
            remoteDataSource.postLogin(request)
        },
        saveToken = {
            localDataSource.saveToken(it)
        }
    )

    fun register(request: RegisterRequest) = performAuthTokenNetworkOperation(
        call = {
            remoteDataSource.postRegister(request)
        },
        saveToken = {
            localDataSource.saveToken(it)
        }
    )

    fun getRestaurants() =
        performNetworkOperation {
            remoteDataSource.getRestaurants()
        }

    fun getRestaurantById(id: String) =
        performNetworkOperation {
            remoteDataSource.getRestaurantById(id)
        }

    fun postRestaurant(restaurantAddRequest: RestaurantAddRequest) =
        performNetworkOperation {
            authRemoteDataSource.postRestaurant(request = restaurantAddRequest)
        }

    fun getMealById(id: String) =
        performNetworkOperation {
            remoteDataSource.getMealById(id)
        }


    fun postMeal(restaurantId: String, mealAddRequest: MealAddRequest) =
        performNetworkOperation {
            authRemoteDataSource.postMeal(restaurantId, request = mealAddRequest)
        }

    fun getRestaurantByCuisine(cuisine: String) =
        performNetworkOperation {
            remoteDataSource.getRestaurantsByCuisine(cuisine)
        }


    fun getOrder() =
        performNetworkOperation {
            authRemoteDataSource.getOrders()
        }

    fun getUser() = performNetworkOperation {
        authRemoteDataSource.getUser()
    }

    fun updateUser(user: UserRequest) = performNetworkOperation {
        authRemoteDataSource.updateUser(request = user)
    }

    fun postOrder(orderAddRequest: OrderAddRequest) =
        performNetworkOperation {
            authRemoteDataSource.postOrder(orderAddRequest)
        }

    fun logOut() {
        localDataSource.saveToken("")
    }

}