package com.example.products.data.mappers

import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import io.kotest.matchers.shouldBe
import org.junit.Test

class ProductResponseMapperTest {
    @Test
    fun `correctly map products`(){
        // given
        val id = 3
        val title = "title"
        val price = 3.3
        val category = "jewelery"
        val imageUrl = null as String?
        val description = "description"
        val mapper = ProductResponseMapper()
        val productResponse = ProductResponse(
            id = id,
            title = title,
            price = price,
            description = description,
            category = category,
            imageUrl = imageUrl
        )

        // when
        val mapped = with(mapper){
            productResponse.toProduct()
        }

        // then
        mapped shouldBe Product(
            id = id,
            title = title,
            price = price,
            description = description,
            category = Category.JEWELRY,
            image = "",
        )
    }
}
