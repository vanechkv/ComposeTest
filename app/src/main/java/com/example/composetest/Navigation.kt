package com.example.composetest

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class Screen {
    object RoleSelection : Screen()
    object AdminProductList : Screen()
    object ClientProductList : Screen()
    data class AddProduct(val product: Product? = null) : Screen()
    data class ProductDetail(val productId: String) : Screen()
}

@Composable
fun ProductApp(productRepository: ProductRepository) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.RoleSelection) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    when (currentScreen) {
        is Screen.RoleSelection -> {
            RoleSelectionScreen(
                onAdminClick = { currentScreen = Screen.AdminProductList },
                onClientClick = { currentScreen = Screen.ClientProductList }
            )
        }
        is Screen.AdminProductList -> {
            ProductListAdminScreen(
                products = productRepository.products.toList(),
                onAddProduct = {
                    editingProduct = null
                    currentScreen = Screen.AddProduct(null)
                },
                onEditProduct = { product ->
                    editingProduct = product
                    currentScreen = Screen.AddProduct(product)
                },
                onDeleteProduct = { productId ->
                    productRepository.deleteProduct(productId)
                },
                onBack = { currentScreen = Screen.RoleSelection }
            )
        }
        is Screen.ClientProductList -> {
            ProductListClientScreen(
                products = productRepository.products.toList(),
                onProductClick = { product ->
                    currentScreen = Screen.ProductDetail(product.id)
                },
                onBack = { currentScreen = Screen.RoleSelection }
            )
        }
        is Screen.AddProduct -> {
            val product = (currentScreen as? Screen.AddProduct)?.product
            AddEditProductScreen(
                product = product,
                onSave = { savedProduct ->
                    if (product == null) {
                        productRepository.addProduct(savedProduct)
                    } else {
                        productRepository.updateProduct(savedProduct)
                    }
                    currentScreen = Screen.AdminProductList
                },
                onCancel = {
                    currentScreen = Screen.AdminProductList
                }
            )
        }
        is Screen.ProductDetail -> {
            val productId = (currentScreen as? Screen.ProductDetail)?.productId
            // Get the current product from repository to ensure we have the latest data
            val product = productId?.let { 
                productRepository.products.find { it.id == productId }
            }
            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBuy = { productToBuy ->
                        val success = productRepository.buyProduct(productToBuy.id, 1)
                        if (success) {
                            // Product will be automatically updated in the repository
                            // and the screen will recompose with the new stock value
                        }
                    },
                    onBack = { currentScreen = Screen.ClientProductList }
                )
            } else {
                // Product not found, show empty state or navigate back
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Product not found")
                        Button(onClick = { currentScreen = Screen.ClientProductList }) {
                            Text("Back to Products")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoleSelectionScreen(
    onAdminClick: () -> Unit,
    onClientClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Product Management System",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = MaterialTheme.typography.headlineMedium.fontWeight
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onAdminClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Admin", style = MaterialTheme.typography.titleLarge)
            }

            Button(
                onClick = onClientClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Client", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

