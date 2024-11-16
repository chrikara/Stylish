package com.example.stylish.navigationroot

import com.example.core.domain.Preferences
import com.example.core.domain.model.UserInfo
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SplashViewModelTest {
    @Test
    fun `if user info is null, user is not logged in`() = runTest {
        // given
        val preferences = mockk<Preferences> {
            coEvery { getUserInfo() } returns null
        }

        // when
        val viewModel = SplashViewModel(preferences)

        // then
        viewModel.isLoggedIn() shouldBe false
    }

    @Test
    fun `if token is empty, user is not logged in`() = runTest {
        // given
        val preferences = mockk<Preferences> {
            coEvery { getUserInfo() } returns UserInfo(token = "")
        }

        // when
        val viewModel = SplashViewModel(preferences)

        // then
        viewModel.isLoggedIn() shouldBe false
    }

    @Test
    fun `if token is blank, user is not logged in`() = runTest {
        // given
        val preferences = mockk<Preferences> {
            coEvery { getUserInfo() } returns UserInfo(token = " ")
        }

        // when
        val viewModel = SplashViewModel(preferences)

        // then
        viewModel.isLoggedIn() shouldBe false
    }

    @Test
    fun `if token is valid, user is logged in`() = runTest {
        // given
        val preferences = mockk<Preferences> {
            coEvery { getUserInfo() } returns UserInfo(token = "token")
        }

        // when
        val viewModel = SplashViewModel(preferences)

        // then
        viewModel.isLoggedIn() shouldBe true
    }
}

