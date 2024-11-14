package com.example.login.presentation

import app.cash.turbine.test
import com.example.core.commonTest.MainDispatcherRule
import com.example.core.commonTest.testFirst
import com.example.core.domain.Preferences
import com.example.core.domain.model.UserInfo
import com.example.login.domain.LoginRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {
    private lateinit var repository: LoginRepository
    private lateinit var preferences: Preferences
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        repository = mockk(relaxed = true) {
            coEvery { login(any(), any()) } returns Result.success(UserInfo())
        }
        preferences = mockk(relaxed = true)
        viewModel = LoginViewModel(repository, preferences)
    }

    @Test
    fun `correctly updates username`() {
        // given
        val username = "Chris"

        // when
        viewModel.onUsernameChanged(username)

        // then
        viewModel.username.value shouldBe username
    }

    @Test
    fun `correctly updates password`() {
        // given
        val password = "12345"

        // when
        viewModel.onPasswordChanged(password)

        // then
        viewModel.password.value shouldBe password
    }

    @Test
    fun `enabled - username valid, password valid, isRunning false`() = runTest {
        // given
        val username = "chris"
        val password = "12345"

        // when
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)

        // then
        viewModel.enabled.testFirst() shouldBe true
    }

    @Test
    fun `enabled - username invalid, password valid, isRunning false`() = runTest {
        // given
        val username = "ch"
        val password = "12345"

        // when
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)

        // then
        viewModel.enabled.testFirst() shouldBe false
    }

    @Test
    fun `enabled - username valid, password invalid, isRunning false`() = runTest {
        // given
        val username = "chris"
        val password = "12"

        // when
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)

        // then
        viewModel.enabled.testFirst() shouldBe false
    }

    @Test
    fun `enabled - username valid, password valid, click next`() = runTest {
        // given
        val username = "chris"
        val password = "12345"

        // then
        viewModel.enabled.test {
            awaitItem() shouldBe false
            viewModel.onUsernameChanged(username)
            viewModel.onPasswordChanged(password)
            awaitItem() shouldBe true

            viewModel.nextClicked({}, {})

            awaitItem() shouldBe false
            awaitItem() shouldBe true
        }
    }

    @Test
    fun `isRunning - username valid, password valid, click next`() = runTest {
        // given
        val username = "chris"
        val password = "12345"

        // when
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)

        // then
        viewModel.isRunning.test {
            awaitItem() shouldBe false

            viewModel.nextClicked({}, {})

            awaitItem() shouldBe true
            awaitItem() shouldBe false
        }
    }

    @Test
    fun `nextClicked - show success upon successful login`() = runTest {
        // given
        val username = "Chris"
        val password = "12345"
        val onSuccess: () -> Unit = mockk(relaxed = true)
        val userInfo = mockk<UserInfo>(relaxed = true)

        // when
        repository = mockk {
            coEvery { login(any(), any()) } returns Result.success(userInfo)
        }
        viewModel = LoginViewModel(repository, preferences)
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)
        viewModel.nextClicked(
            onSuccess = onSuccess,
            onShowErrorMessage = {},
        )

        advanceUntilIdle()

        // then
        coVerify {
            preferences.setUserInfo(userInfo)
            onSuccess()
        }
    }

    @Test
    fun `nextClicked - show error upon unsuccessful login`() = runTest {
        // given
        val username = "Chris"
        val password = "12345"
        val onShowErrorMessage: () -> Unit = mockk(relaxed = true)

        // when
        repository = mockk {
            coEvery { login(any(), any()) } returns Result.failure(Exception())
        }
        viewModel = LoginViewModel(repository, preferences)
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)
        viewModel.nextClicked(
            onSuccess = {},
            onShowErrorMessage = onShowErrorMessage,
        )

        advanceUntilIdle()

        // then
        verify {
            onShowErrorMessage()
        }
    }
}
