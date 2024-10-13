package com.example.foodapp.food_home_feature.data.repository

import com.example.foodapp.food_home_feature.data.remote.FoodApi
import com.example.foodapp.food_home_feature.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val foodApi: FoodApi
) : HomeRepository {
    override suspend fun getCategories() = foodApi.getCategories()

    override suspend fun getProducts() = foodApi.getProducts()
}