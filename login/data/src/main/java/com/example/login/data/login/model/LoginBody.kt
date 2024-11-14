package com.example.login.data.login.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    @SerialName("username")
    val username: String? = null,

    @SerialName("password")
    val password: String? = null,
)
