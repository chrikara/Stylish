package com.example.core.presentation.components

import kotlinx.serialization.Serializable

sealed interface Screen {
    data object Splash : Screen
    data object Login : Screen
    data object Products : Screen

    @Serializable
    data class Edit(val id: Int) : Screen
}
