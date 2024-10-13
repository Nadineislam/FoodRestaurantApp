package com.example.foodapp.food_home_feature.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.foodapp.R
import com.example.foodapp.food_home_feature.data.remote.models.Product
import com.example.foodapp.food_home_feature.presentation.viewmodel.HomeViewModel

@Composable
fun PricingInfo(price: String, discount: String) {
    val priceAfterDiscount = (price.toFloat() - discount.toFloat()).let {
        if (it < 0) 0.0f else it
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = String.format("%.2f EGP", priceAfterDiscount),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Green,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = String.format("%.2f EGP", price.toFloat()),
            fontSize = 14.sp,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.LineThrough
            )
        )
    }
}

@Composable
fun IconWithCircle() {
    Box(
        modifier = Modifier
            .size(40.dp)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.group_1),
            contentDescription = "Left Icon",
            modifier = Modifier
                .size(29.dp)
                .align(Alignment.TopStart)
                .padding(end = 5.dp),
            tint = Color.Black
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(29.dp)
                .align(Alignment.TopEnd),
            tint = Color.Black
        )
    }
}

@Composable
fun IconWithTextAndChips(viewModel: HomeViewModel = hiltViewModel()) {
    val categoriesResource by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategoryIndex by viewModel.selectedCategoryIndex.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            text = "Let's Find Your Favorite Food",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 2,
        )

        Spacer(modifier = Modifier.height(16.dp))

        GetResourceList(
            resourceState = categoriesResource,
            emptyListMessage = "No categories available"
        ) { categories ->
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories?.size ?: 0) { index ->
                    categories?.get(index)?.let { category ->
                        Chip(
                            label = category.name,
                            isSelected = selectedCategoryIndex == index,
                            onChipSelected = { viewModel.selectCategory(index) }
                        )
                    }
                }
    }}}
}


@Composable
fun Chip(
    label: String,
    isSelected: Boolean,
    onChipSelected: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) colorResource(id = R.color.dark_blue) else Color.LightGray
            )
            .clickable { onChipSelected() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun ProductScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavController) {
    val productsResource by viewModel.products.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(6.dp))

        GetResourceList(
            resourceState = productsResource,
            emptyListMessage = "No products available"
        ) { products ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(products?.size ?: 0) { index ->
                    val product = products?.get(index)
                    product?.let {
                        ProductCard(product = it, navController = navController)
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(product: Product, navController: NavController) {
    var isFavorited by remember { mutableStateOf(product.favorite == 1) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .clickable {
                navController.navigate("product_details/${product.id}")
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                val painter= rememberAsyncImagePainter(model = product.productImage)
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable {
                            isFavorited = !isFavorited
                        }
                )

                IconButton(
                    onClick = {
                        isFavorited = !isFavorited
                    },
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.Top)
                ) {
                    val tintColor =
                        if (isFavorited) colorResource(id = R.color.dark_blue) else colorResource(id = R.color.black)
                    Image(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = "Favorite",
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(tintColor)
                    )
                }
            }

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(bottom = 2.dp),
                maxLines = 1
            )

            StarRating(rate = product.rate)

            PricingInfo(price = product.price, discount = product.discount)
        }
    }
}


@Composable
fun StarRating(rate: String) {
    val starCount = rate.toFloat().toInt()

    Row {
        repeat(5) { index ->
            val starColor = if (index < starCount) Color.Yellow else Color.Gray
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = starColor,
                modifier = Modifier
                    .size(16.dp)
                    .padding(bottom = 2.dp)
            )
        }
    }
}