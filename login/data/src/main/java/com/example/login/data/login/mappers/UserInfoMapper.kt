package com.example.login.data.login.mappers

import com.example.core.domain.model.UserInfo
import com.example.login.data.login.model.LoginResponse

internal fun LoginResponse.toUserInfo() = UserInfo(
    username = "",
    password = "",
    token = token ?: "",
)
