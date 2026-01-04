package com.example.composetest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class ProductRepository {
    private val _products = mutableStateListOf<Product>()
    val products: SnapshotStateList<Product> = _products

    init {
        // Initialize with some sample products
        _products.addAll(
            listOf(
                Product(
                    id = "1",
                    name = "Laptop",
                    description = "High-performance laptop for work and gaming",
                    price = 999.99,
                    stock = 10,
                    imageRes = null
                ),
                Product(
                    id = "2",
                    name = "Smartphone",
                    description = "Latest model with advanced features",
                    price = 699.99,
                    stock = 25,
                    imageRes = null
                ),
                Product(
                    id = "3",
                    name = "Headphones",
                    description = "Wireless noise-cancelling headphones",
                    price = 199.99,
                    stock = 50,
                    imageRes = null
                )
            )
        )
    }

    fun addProduct(product: Product) {
        val newProduct = product.copy(id = System.currentTimeMillis().toString())
        _products.add(newProduct)
    }

    fun updateProduct(product: Product) {
        val index = _products.indexOfFirst { it.id == product.id }
        if (index != -1) {
            _products[index] = product
        }
    }

    fun deleteProduct(productId: String) {
        _products.removeAll { it.id == productId }
    }

    fun getProductById(productId: String): Product? {
        return _products.find { it.id == productId }
    }

    fun buyProduct(productId: String, quantity: Int = 1): Boolean {
        val product = getProductById(productId)
        return if (product != null && product.stock >= quantity) {
            val updatedProduct = product.copy(stock = product.stock - quantity)
            updateProduct(updatedProduct)
            true
        } else {
            false
        }
    }
}

