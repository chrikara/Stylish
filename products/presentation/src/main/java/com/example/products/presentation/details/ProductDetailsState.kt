package com.example.products.presentation.details

import com.example.core.presentation.components.ScreenState
import com.example.products.domain.model.Product

internal data class ProductDetailsState(
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val category: String = "",
    val isRunning: Boolean = false,
    val product: Product? = null,
    val screenState: ScreenState = ScreenState.LOADING
)
