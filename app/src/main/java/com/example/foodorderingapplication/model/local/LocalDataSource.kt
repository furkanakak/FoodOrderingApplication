package com.example.foodorderingapplication.model.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val sharedPrefManager: SharedPrefManager) {

    fun saveToken(token: String) {
        sharedPrefManager.saveToken(token)
    }

    fun getToken(): String? {
        return sharedPrefManager.getToken()
    }
}