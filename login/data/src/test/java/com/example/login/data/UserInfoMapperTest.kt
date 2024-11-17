package com.example.login.data

import com.example.core.domain.model.UserInfo
import com.example.login.data.login.mappers.UserInfoMapper
import com.example.login.data.login.model.LoginResponse
import io.kotest.matchers.shouldBe
import org.junit.Test

class UserInfoMapperTest {
    @Test
    fun `UserInfoMapper - token null`() {
        // given
        val mapper = UserInfoMapper()
        val response = LoginResponse(token = null)

        // when
        val mapped = with(mapper) {
            response.toUserInfo()
        }

        // then
        mapped shouldBe UserInfo(
            username = "",
            password = "",
            token = "",
        )
    }

    @Test
    fun `UserInfoMapper - token not null`() {
        // given
        val token = "token"
        val mapper = UserInfoMapper()
        val response = LoginResponse(token = token)

        // when
        val mapped = with(mapper) {
            response.toUserInfo()
        }

        // then
        mapped shouldBe UserInfo(
            username = "",
            password = "",
            token = token,
        )
    }
}
