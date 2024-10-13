package com.example.foodapp.food_home_feature.data.remote.models


data class ProductResponse(
    val status: Boolean,
    val data: List<Product>
)