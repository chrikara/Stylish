package com.example.products.data

import com.example.core.network.FakeStoreApi
import com.example.core.network.get
import com.example.core.network.put
import com.example.products.data.mappers.CategoryResponseMapper
import com.example.products.data.mappers.ProductResponseMapper
import com.example.products.domain.ProductsRepository
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import io.ktor.client.engine.HttpClientEngine

typealias ProductResponse = com.example.products.data.model.Product

class ProductsRepositoryImpl(
    override val httpClientEngine: HttpClientEngine? = null,
    private val productResponseMapper: ProductResponseMapper,
    private val categoryResponseMapper: CategoryResponseMapper,
) : FakeStoreApi, ProductsRepository {
    override suspend fun getCategories(): Result<List<Category>> = get<List<String>>(
        endpoint = "/products/categories"
    ).map { strings ->
        with(categoryResponseMapper) {
            strings.toCategories()
        }
    }

    override suspend fun getProducts() = get<List<ProductResponse>>(
        endpoint = "/products"
    ).map { productsList ->
        with(productResponseMapper) {
            productsList.map { it.toProduct() }
        }
    }

    override suspend fun getProduct(id: Int): Result<Product> = get<ProductResponse>(
        endpoint = "/products/$id"
    ).map {
        with(productResponseMapper) {
            it.toProduct()
        }
    }

    override suspend fun updateProduct(
        id: Int,
        title: String,
        price: Double,
        description: String,
        category: String
    ): Result<Unit> = put(
        endpoint = "/products/$id",
        body = ProductResponse(
            id = id,
            title = title,
            price = price,
            description = description,
            category = category
        )
    )
}
