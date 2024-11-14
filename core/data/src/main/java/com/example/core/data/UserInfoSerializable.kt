package com.example.core.data

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoSerializable(
    val username: String,
    val password: String,
    val token: String,
)
