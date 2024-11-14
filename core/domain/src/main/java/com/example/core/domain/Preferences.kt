package com.example.core.domain

import com.example.core.domain.model.UserInfo

interface Preferences {
    suspend fun getUserInfo(): UserInfo?
    suspend fun setUserInfo(userInfo: UserInfo?)
}
