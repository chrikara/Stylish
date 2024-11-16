package com.example.products.data.mappers

import com.example.products.domain.model.Category

class CategoryResponseMapper {
    fun List<String>.toCategories() = mapNotNull { Category.fromString(it) }
}
