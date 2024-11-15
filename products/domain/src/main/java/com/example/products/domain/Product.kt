package com.example.products.domain

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val category: Category,
    val image: String,
    val description: String,
)
