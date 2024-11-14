package com.example.login.domain

interface LoginRepository {
    suspend fun login(username: String, password: String): Result<String>
}
