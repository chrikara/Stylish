package com.example.products.data.mappers

import com.example.products.domain.model.Category
import io.kotest.matchers.shouldBe
import org.junit.Test

class CategoryResponseMapperTest {
    @Test
    fun `correctly map categories`(){
        // given
        val mapper = CategoryResponseMapper()
        val strings = listOf("jewelr", "bad", "electronics")

        // when
        val mapped = with(mapper){
            strings.toCategories()
        }

        // then
        mapped shouldBe listOf(Category.ELECTRONICS)
    }
}
