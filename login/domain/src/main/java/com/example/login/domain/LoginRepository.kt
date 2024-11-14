package com.example.login.domain

import com.example.core.domain.model.UserInfo


interface LoginRepository {
    suspend fun login(username: String, password: String): Result<UserInfo>
}
