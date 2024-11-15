package com.example.products.domain

import com.example.products.domain.model.Category
import com.example.products.domain.model.Product


interface ProductsRepository {

    suspend fun getCategories(): Result<List<Category>>

    suspend fun getProducts(): Result<List<Product>>
}
