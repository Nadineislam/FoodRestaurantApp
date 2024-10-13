package com.example.foodapp.food_home_feature.domain.repository

import com.example.foodapp.food_home_feature.data.remote.models.CategoryResponse
import com.example.foodapp.food_home_feature.data.remote.models.ProductResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getCategories(): Response<CategoryResponse>
    suspend fun getProducts(): Response<ProductResponse>
}
