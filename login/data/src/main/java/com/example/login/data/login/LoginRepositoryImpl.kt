package com.example.login.data.login

import com.example.core.network.FakeStoreApi
import com.example.core.network.post
import com.example.login.data.login.model.LoginBody
import com.example.login.domain.LoginRepository

internal class LoginRepositoryImpl : FakeStoreApi, LoginRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<String> = post(
        endpoint = "/auth/login",
        body = LoginBody(
            username = username,
            password = password,
        )
    )
}
