package com.example.products.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("category") val category: String? = null,
    @SerialName("image") val imageUrl: String? = null,
    @SerialName("rating") val rating: Rating? = null,
)

@Serializable
data class Rating(
    @SerialName("rate") val rate: Double? = null,
    @SerialName("count") val count: Int? = null,
)
