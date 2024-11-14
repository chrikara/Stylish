package com.example.stylish.data.login

import com.example.stylish.data.login.model.LoginBody
import com.example.stylish.data.network.Api
import com.example.stylish.data.network.FakeStoreApi
import com.example.stylish.data.network.post
import com.example.stylish.domain.login.LoginRepository

class LoginRepositoryImpl : FakeStoreApi, LoginRepository {
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
