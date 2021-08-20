package com.example.foodorderingapplication.utils

import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            val errorBody = response.errorBody().toString()
            return error("${response.code()} - $errorBody")
        } catch (err: Exception) {
            return error("${err.localizedMessage} - ${err.message}")
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network error: $message")
    }
}