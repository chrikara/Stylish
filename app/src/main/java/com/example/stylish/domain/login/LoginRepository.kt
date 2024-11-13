package com.example.stylish.domain.login

interface LoginRepository {
    suspend fun login(username : String, password : String) : Result<String>
}
