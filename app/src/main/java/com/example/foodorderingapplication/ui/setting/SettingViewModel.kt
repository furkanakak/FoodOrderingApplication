package com.example.foodorderingapplication.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.foodorderingapplication.model.ApiRepository
import com.example.foodorderingapplication.model.entity.User
import com.example.foodorderingapplication.model.entity.profile.UserRequest
import com.example.foodorderingapplication.model.entity.profile.UserResponse
import com.example.foodorderingapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private var apiRepository: ApiRepository
) : ViewModel() {

    fun getUser(): LiveData<Resource<UserResponse>> = apiRepository.getUser()

    fun logOut() {
        apiRepository.logOut()
    }

    fun updateUser(userRequest: UserRequest): LiveData<Resource<User>> =
        apiRepository.updateUser(userRequest)

}