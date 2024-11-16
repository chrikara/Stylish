package com.example.products.data.mappers

import com.example.products.domain.model.Category
import com.example.products.domain.model.Product

typealias ProductResponse = com.example.products.data.model.Product


class ProductResponseMapper{
    fun ProductResponse.toProduct() = Product(
        id = id,
        title = title,
        price = price,
        category = Category.fromString(category),
        image = imageUrl ?: "",
        description = description,
    )
}

