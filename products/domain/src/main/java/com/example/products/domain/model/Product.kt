package com.example.products.domain.model

import com.example.products.domain.model.Category.ELECTRONICS
import com.example.products.domain.model.Category.JEWELRY
import com.example.products.domain.model.Category.MENS_CLOTHING

data class Product(
    val id: Int,
    val title: String? = null,
    val price: Double? = null,
    val category: Category? = null,
    val image: String,
    val description: String? = null,
)

val Product.uniqueKey: Int
    get() = id

val fakeProducts = listOf(
    Product(
        id = 1,
        title = "Men's Casual Shirt",
        price = 29.99,
        category = MENS_CLOTHING,
        image = "",
        description = "A stylish casual shirt for men, perfect for everyday wear."
    ),
    Product(
        id = 2,
        title = "Elegant Diamond Necklace",
        price = 499.99,
        category = JEWELRY,
        image = "",
        description = "An elegant diamond necklace that adds a touch of sophistication."
    ),
    Product(
        id = 3,
        title = "Wireless Noise-Cancelling Headphones",
        price = 199.99,
        category = ELECTRONICS,
        image = "",
        description = "High-quality wireless headphones with active noise cancellation."
    ),
    Product(
        id = 4,
        title = "Noise headphones",
        price = 199.99,
        category = ELECTRONICS,
        image = "",
        description = "High-quality wireless headphones with active noise cancellation."
    )
)
