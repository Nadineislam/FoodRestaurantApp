package com.example.foodapp.core.utils

import retrofit2.Response

inline fun <T, R> handleResponse(response: Response<T>, transform: (T) -> R): Resource<R> {
    return if (response.isSuccessful) {
        response.body()?.let { resultResponse ->
            Resource.Success(transform(resultResponse))
        } ?: Resource.Error("Response body is null")
    } else {
        Resource.Error("Failed with code: ${response.code()}, message: ${response.message()}")
    }
}