package com.example.foodorderingapplication.ui.restaurantadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodorderingapplication.model.ApiRepository
import com.example.foodorderingapplication.model.entity.restaurantadd.RestaurantAddRequest
import com.example.foodorderingapplication.model.entity.restaurantadd.RestaurantAddResponse
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RestaurantAddViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository
) : ViewModel() {

    fun addRestaurant(
        name: String,
        cuisine: String,
        deliveryInfo: String,
        deliveryTime: String,
        imageUrl: String,
        address: String,
        district: String,
        minDeliveryFee: String,
        paymentMethods: String,
        phone: String,
        website: String,
    ): LiveData<Resource<RestaurantAddResponse>> {

        val request = RestaurantAddRequest(
            name, cuisine, deliveryInfo, deliveryTime,
            imageUrl, address, district, minDeliveryFee, paymentMethods, phone, website
        )
        return apiRepository.postRestaurant(request)
    }
}