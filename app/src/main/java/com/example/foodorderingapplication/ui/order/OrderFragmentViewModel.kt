package com.example.foodorderingapplication.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodorderingapplication.model.ApiRepository
import com.example.foodorderingapplication.model.entity.order.OrderResponse
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderFragmentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiRepository: ApiRepository
) : ViewModel() {

    fun getOrders(): LiveData<Resource<OrderResponse>> =
        apiRepository.getOrder()

}