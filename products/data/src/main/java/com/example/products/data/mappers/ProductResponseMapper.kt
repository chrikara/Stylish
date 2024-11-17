package com.example.products.data.mappers

import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import javax.inject.Inject

typealias ProductResponse = com.example.products.data.model.Product


class ProductResponseMapper @Inject constructor() {
    fun ProductResponse.toProduct() = Product(
        id = id,
        title = title,
        price = price,
        category = Category.fromString(category),
        image = imageUrl ?: "",
        description = description,
    )
}

