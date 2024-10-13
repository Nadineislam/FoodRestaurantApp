package com.example.foodapp.food_home_feature.data.remote.models

import com.google.gson.annotations.SerializedName

data class Product(
    val id: Int,
    val name: String,
    val rate: String,
    val price: String,
    val discount: String,
    val favorite: Int,
    @SerializedName("product_image")
    val productImage: String,
    @SerializedName("restaurant_id")
    val restaurantId: Int,
    @SerializedName("restaurant_image")
    val restaurantImage: String,
    @SerializedName("restaurant_name")
    val restaurantName: String
)
