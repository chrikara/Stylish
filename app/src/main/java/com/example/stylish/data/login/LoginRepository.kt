package com.example.stylish.data.login

import com.example.stylish.data.network.Api
import com.example.stylish.data.network.post
import com.example.stylish.domain.login.LoginRepository
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class LoginRepositoryImpl : Api(), LoginRepository {
    override suspend fun login(
        username: String,
        password: String,
    ) : Result<String> = post(
        endpoint = "/auth/login",
        body = LoginBody(
            username = username,
            password = password,
        )
    )

}

@Serializable
data class LoginBody(
    @SerialName("username")
    val username: String? = null,

    @SerialName("password")
    val password: String? = null,
)
