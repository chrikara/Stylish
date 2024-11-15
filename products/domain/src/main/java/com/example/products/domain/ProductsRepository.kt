package com.example.products.domain

import com.example.products.domain.model.Category
import com.example.products.domain.model.Product


interface ProductsRepository {

    suspend fun getCategories(): Result<List<Category>>

    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProduct(id: Int): Result<Product>

    suspend fun updateProduct(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String,
    ): Result<Unit>
}
