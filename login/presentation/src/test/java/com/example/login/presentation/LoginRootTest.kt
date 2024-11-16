package com.example.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.core.commonTest.AndroidTest
import com.example.core.commonTest.hasDrawable
import com.example.core.commonTest.hasTestTag
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import com.example.core.presentation.R as uikitR

class LoginRootTest : AndroidTest() {


    @Test
    fun `toggle password, closed eye icon is `() {
        // when
        rule.setContent {
            LoginRootUnderTest()
        }

        rule.onNode(hasDrawable(R.drawable.password)).assertIsDisplayed()
        rule.onNode(hasTestTag(uikitR.string.stylish_text_field_trailing_icon_test_tag))
            .performClick()

        // then
        rule.onNode(hasDrawable(R.drawable.closed_eye)).assertIsDisplayed()
    }

    @Test
    fun `type username, text is displayed`() {
        // given
        val username = "Chris"

        // when
        rule.setContent {
            LoginRootUnderTest()
        }

        rule.onNode(hasTestTag(R.string.username_text_field_test_tag))
            .performTextInput(username)

        // then
        rule.onNode(hasText(username)).assertIsDisplayed()
    }

    @Test
    fun `type password, text is displayed`() {
        // given
        val password = "Chris"

        // when
        rule.setContent {
            LoginRootUnderTest()
        }

        rule.onNode(hasTestTag(R.string.password_text_field_test_tag))
            .performTextInput(password)

        // then
        rule.onNode(hasText(password)).assertIsDisplayed()
    }

    @Test
    fun `if button is enabled, assert has correct click action`() {
        // given
        val enabled = true
        val onLogin: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent {
            LoginRootUnderTest(
                enabled = enabled,
                onLogin = onLogin,
            )
        }

        rule.onNode(hasTestTag(R.string.login_button_test_tag))
            .performClick()

        // then
        verify {
            onLogin()
        }
    }

    @Test
    fun `if is running, assert has  progress indicator`() {
        // given
        val isRunning = true

        // when
        rule.setContent {
            LoginRootUnderTest(
                isRunning = isRunning,
            )
        }

        // then
        rule.onNode(hasTestTag(R.string.login_progress_indicator_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun `if is not running, assert progress indicator does not exist`() {
        // given
        val isRunning = false

        // when
        rule.setContent {
            LoginRootUnderTest(
                isRunning = isRunning,
            )
        }

        // then
        rule.onNode(hasTestTag(R.string.login_progress_indicator_test_tag))
            .assertDoesNotExist()
    }


    @Composable
    private fun LoginRootUnderTest(
        enabled: Boolean = true,
        isRunning: Boolean = false,
        onLogin: () -> Unit = {},
    ) {
        var username by remember {
            mutableStateOf("")
        }

        var password by remember {
            mutableStateOf("")
        }

        LoginRoot(
            username = username,
            onUsernameChanged = { username = it },
            password = password,
            onPasswordChanged = { password = it },
            enabled = enabled,
            isRunning = isRunning,
            onLogin = onLogin,
        )
    }
}
