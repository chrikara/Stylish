package com.example.core.data.mapper

import com.example.core.data.UserInfoSerializable
import com.example.core.domain.model.UserInfo

fun UserInfo.toUserInfoSerializable(): UserInfoSerializable =
    UserInfoSerializable(
        username = username,
        password = password,
        token = token,
    )

fun UserInfoSerializable.toUserInfo(): UserInfo =
    UserInfo(
        username = username,
        password = password,
        token = token,
    )
