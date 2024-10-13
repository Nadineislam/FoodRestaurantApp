package com.example.foodapp.food_home_feature.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.foodapp.R
import com.example.foodapp.core.utils.Resource
import com.example.foodapp.food_home_feature.presentation.viewmodel.HomeViewModel

@Composable
fun <T> GetResourceList(
    resourceState: Resource<T>,
    emptyListMessage: String,
    successBlock: @Composable (T?) -> Unit
) {
    when (resourceState) {
        is Resource.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success -> {
            val items = resourceState.data
            if (items != null && (items is List<*> && items.isNotEmpty())) {
                successBlock(items)
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emptyListMessage, color = Color.Gray)
                }
            }
        }

        is Resource.Error -> {
            val message = resourceState.message ?: emptyListMessage
            Toast.makeText(LocalContext.current, message, Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
fun ProductDetailsScreen(
    productId: Int,
    onBackClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val productsResource by viewModel.products.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GetResourceList(
            resourceState = productsResource,
            emptyListMessage = "Product not found"
        ) { products ->
            val selectedProduct = products?.find { it.id == productId }
            selectedProduct?.let {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    selectedProduct.let { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                IconButton(onClick = onBackClick) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_back),
                                        contentDescription = "Back",
                                        tint = Color.Black
                                    )
                                }
                                Text(
                                    text = "Details",
                                    style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                                IconButton(onClick = {  }) {
                                    Icon(
                                        painterResource(id = R.drawable.vector),
                                        contentDescription = "Favorite",
                                        tint = Color.Black
                                    )
                                }
                            }

                            val painter= rememberAsyncImagePainter(model =product.productImage)
                            Image(
                                painter =painter,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Meal Name
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(9.dp)
                                )

                                PricingInfo(price = product.price, discount = product.discount)
                            }

                            Text(
                                text = "Text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard.",
                                fontSize = 12.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 22.dp)
                            )

                            Row {
                                repeat(5) { index ->
                                    val starColor = if (index < product.rate.toFloat()
                                            .toInt()
                                    ) Color.Yellow else Color.Gray
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = starColor,
                                        modifier = Modifier
                                            .padding(vertical = 6.dp, horizontal = 1.dp)
                                            .size(19.dp)
                                    )
                                }
                            }

                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 50.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    onClick = { /* Handle WhatsApp click */ },
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF00E676
                                        )
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.group),
                                        contentDescription = "WhatsApp",
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "WhatsApp", color = Color.White)
                                }

                                Button(
                                    onClick = { /* Handle Call click */ },
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(
                                            0xFF3F3F6F
                                        )
                                    )
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.vector__1_),
                                        contentDescription = "Call",
                                        tint = Color.White,
                                        modifier = Modifier.size(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = "Call", color = Color.White)
                                }
                            }
                        }
                    }
                }

            }
        }
    }}
