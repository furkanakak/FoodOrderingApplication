package com.example.foodorderingapplication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodorderingapplication.model.ApiRepository
import com.example.foodorderingapplication.model.entity.login.LoginRequest
import com.example.foodorderingapplication.model.entity.login.LoginResponse
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    var savedStateHandle: SavedStateHandle,
    private var apiRepository: ApiRepository
) : ViewModel() {
    fun login(email: String, password: String): LiveData<Resource<LoginResponse>> {
        val request = LoginRequest(email, password)
        return apiRepository.login(request)
    }
}