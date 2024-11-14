package com.example.stylish.ui.presentation.features.login

import app.cash.turbine.test
import com.example.stylish.MainDispatcherRule
import com.example.stylish.domain.login.LoginRepository
import com.example.stylish.testFirst
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private lateinit var repository: LoginRepository
    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        repository = mockk(relaxed = true) {
            coEvery { login(any(), any()) } returns Result.success("1")
        }
        viewModel = LoginViewModel(repository)
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
        val onSuccess: (String) -> Unit = mockk(relaxed = true)
        val token = "token"

        // when
        repository = mockk {
            coEvery { login(any(), any()) } returns Result.success(token)
        }
        viewModel = LoginViewModel(repository)
        viewModel.onUsernameChanged(username)
        viewModel.onPasswordChanged(password)
        viewModel.nextClicked(
            onSuccess = onSuccess,
            onShowErrorMessage = {},
        )

        advanceUntilIdle()

        // then
        verify {
            onSuccess(token)
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
        viewModel = LoginViewModel(repository)
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
