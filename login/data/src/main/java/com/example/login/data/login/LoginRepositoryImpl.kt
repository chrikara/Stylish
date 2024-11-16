package com.example.login.data.login

import com.example.core.domain.model.UserInfo
import com.example.core.network.FakeStoreApi
import com.example.core.network.post
import com.example.login.data.login.model.LoginBody
import com.example.login.data.login.model.LoginResponse
import com.example.login.domain.LoginRepository
import io.ktor.client.engine.HttpClientEngine

internal class LoginRepositoryImpl(
    override val httpClientEngine: HttpClientEngine? = null,
    val userInfoMapper: UserInfoMapper = UserInfoMapper(),
) : FakeStoreApi, LoginRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<UserInfo> = with(userInfoMapper) {
        post<LoginBody, LoginResponse>(
            endpoint = "/auth/login",
            body = LoginBody(
                username = username,
                password = password,
            )
        ).map { it.toUserInfo() }
    }
}

internal class UserInfoMapper {
    fun LoginResponse.toUserInfo() = UserInfo(
        username = "",
        password = "",
        token = token ?: "",
    )
}
