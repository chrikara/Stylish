package com.example.products.domain.model

data class Product(
    val id: Int,
    val title: String? = null,
    val price: Double? = null,
    val category: Category? = null,
    val image: String,
    val description: String? = null,
)
