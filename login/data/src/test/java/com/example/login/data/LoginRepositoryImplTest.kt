package com.example.login.data

import com.example.core.domain.model.UserInfo
import com.example.core.network.NetworkTest
import com.example.login.data.login.LoginRepositoryImpl
import com.example.login.data.login.mappers.UserInfoMapper
import com.example.login.data.login.model.LoginResponse
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LoginRepositoryImplTest : NetworkTest() {
    private val userInfoMapper = mockk<UserInfoMapper>(relaxed = true)
    private lateinit var repository: LoginRepositoryImpl

    @Before
    fun setup() {
        repository = LoginRepositoryImpl(
            httpClientEngine = getTestHttpClientEngine(),
            userInfoMapper = userInfoMapper,
        )
    }

    @Test
    fun `call repository, correct body is set`() = runTest {
        // given
        val username = "username"
        val password = "password"

        // when
        repository.login(username, password)

        // then
        request.shouldNotBeNull()
        request?.method?.value shouldBe "POST"
        request?.url?.host shouldBe "fakestoreapi.com"
        request?.url?.encodedPath shouldBe "/auth/login"
    }

    @Test
    fun `when request is successful, correct result is returned`() = runTest {
        // given
        val token = "token"
        with(userInfoMapper) {
            every { any<LoginResponse>().toUserInfo() } returns UserInfo(token = token)
        }

        // when
        val result = repository.login("username", "username")

        // then
        result.getOrNull()?.token shouldBe token
    }

    @Test
    fun `when request is unsuccessful, correct result is returned`() = runTest {
        // given
        val repository = LoginRepositoryImpl(
            userInfoMapper = userInfoMapper,
            httpClientEngine = getTestHttpClientEngine(isSuccessful = false),
        )

        // when
        val result = repository.login("username", "username")

        // then
        result.isFailure shouldBe true
        result.getOrNull()?.token shouldBe null
    }

    private fun getTestHttpClientEngine(isSuccessful: Boolean = true) =
        getHttpClientEngine(response = Unit, isSuccessful = isSuccessful)
}
