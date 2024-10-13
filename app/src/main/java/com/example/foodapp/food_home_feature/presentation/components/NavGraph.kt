package com.example.foodapp.food_home_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(startDestination: String = "productList") {
    val navController = rememberNavController()

    NavHost(navController, startDestination = startDestination) {
        composable("productList") {
            IconWithCircle()

            Column {
                IconWithTextAndChips()
                ProductScreen(navController = navController)
            }
        }
        composable("product_details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailsScreen(productId = productId,{ navController.popBackStack() })
        }
    }
}