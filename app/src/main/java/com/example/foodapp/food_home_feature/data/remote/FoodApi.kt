package com.example.foodapp.food_home_feature.data.remote

import com.example.foodapp.food_home_feature.data.remote.models.CategoryResponse
import com.example.foodapp.food_home_feature.data.remote.models.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface FoodApi {
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>
    @GET("products.php")
    suspend fun getProducts(): Response<ProductResponse>

}