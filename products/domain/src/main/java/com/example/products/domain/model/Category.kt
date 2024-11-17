package com.example.products.domain.model

import com.example.products.domain.model.Category.ELECTRONICS
import com.example.products.domain.model.Category.JEWELRY
import com.example.products.domain.model.Category.MENS_CLOTHING
import com.example.products.domain.model.Category.WOMENS_CLOTHING

enum class Category {
    MENS_CLOTHING,
    JEWELRY,
    ELECTRONICS,
    WOMENS_CLOTHING;

    companion object {
        fun fromString(text: String?): Category? {
            return when (text) {
                "men's clothing" -> MENS_CLOTHING
                "jewelery" -> JEWELRY
                "electronics" -> ELECTRONICS
                "women's clothing" -> WOMENS_CLOTHING
                else -> null
            }
        }
    }
}

val fakeCategories = listOf(
    ELECTRONICS,
    MENS_CLOTHING,
    WOMENS_CLOTHING,
    JEWELRY,
)

