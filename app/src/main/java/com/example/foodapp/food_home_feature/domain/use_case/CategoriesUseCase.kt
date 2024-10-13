package com.example.foodapp.food_home_feature.domain.use_case

import com.example.foodapp.food_home_feature.domain.repository.HomeRepository
import javax.inject.Inject

class CategoriesUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke() = homeRepository.getCategories()
}