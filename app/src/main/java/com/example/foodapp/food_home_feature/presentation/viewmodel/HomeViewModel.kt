package com.example.foodapp.food_home_feature.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.core.utils.Resource
import com.example.foodapp.core.utils.handleResponse
import com.example.foodapp.food_home_feature.data.remote.models.Category
import com.example.foodapp.food_home_feature.data.remote.models.Product
import com.example.foodapp.food_home_feature.domain.use_case.CategoriesUseCase
import com.example.foodapp.food_home_feature.domain.use_case.ProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoriesUseCase: CategoriesUseCase,
    private val productsUseCase: ProductsUseCase
) : ViewModel() {

    private val _categories = MutableStateFlow<Resource<List<Category>>>(Resource.Loading())
    val categories = _categories.asStateFlow()

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val products = _products.asStateFlow()

    private val _selectedCategoryIndex = MutableStateFlow(0)
    val selectedCategoryIndex = _selectedCategoryIndex.asStateFlow()

    init {
        fetchCategories()
        fetchProducts()
    }


    private fun fetchProducts() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productsResponse = productsUseCase()
                _products.value = handleResponse(productsResponse) { it.data }
            } catch (e: Exception) {
                _products.value = Resource.Error("An error occurred: ${e.localizedMessage}")
            }
        }


    private fun fetchCategories() =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categoriesResponse = categoriesUseCase()
                _categories.value = handleResponse(categoriesResponse) { it.data }
            } catch (e: Exception) {
                _categories.value = Resource.Error("An error occurred: ${e.localizedMessage}")
            }
        }


    fun selectCategory(index: Int) {
        _selectedCategoryIndex.value = index
    }
}
