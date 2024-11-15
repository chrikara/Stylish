package com.example.products.domain.model

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
