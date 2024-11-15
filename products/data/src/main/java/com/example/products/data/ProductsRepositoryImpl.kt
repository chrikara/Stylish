package com.example.products.data

import com.example.core.network.FakeStoreApi
import com.example.core.network.get
import com.example.products.data.mappers.toProduct
import com.example.products.domain.ProductsRepository
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product

typealias ProductResponse = com.example.products.data.model.Product

class ProductsRepositoryImpl : FakeStoreApi, ProductsRepository {
    override suspend fun getCategories(): Result<List<Category>> = get<List<String>>(
        endpoint = "/products/categories"
    ).map { categories -> categories.mapNotNull { Category.fromString(it) } }

    override suspend fun getProducts(): Result<List<Product>> = get<List<ProductResponse>>(
        endpoint = "/products"
    ).map { productsList -> productsList.map { it.toProduct() } }
}
