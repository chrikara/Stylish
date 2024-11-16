package com.example.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.toast.showSingleToast
import com.example.core.presentation.components.uikit.StylishButton
import com.example.core.presentation.components.uikit.StylishTextField

@Composable
fun LoginRoot(
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()
    val isRunning by viewModel.isRunning.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LoginRoot(
        username = username,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        password = password,
        enabled = enabled,
        isRunning = isRunning,
        onLogin = {
            viewModel.nextClicked(
                onSuccess = {
                    context.showSingleToast(R.string.login_was_successful)
                    onSuccessfulLogin()
                },
                onShowErrorMessage = {
                    context.showSingleToast(R.string.something_went_wrong)
                }
            )
        }
    )
}

@Composable
internal fun LoginRoot(
    username: String = "",
    onUsernameChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    isRunning: Boolean = false,
    enabled: Boolean = true,
    onLogin: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .safeDrawingPadding(),
    ) {
        var passwordToggled by remember {
            mutableStateOf(false)
        }

        Spacer(modifier = Modifier.height(31.dp))

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(31.dp))

        StylishTextField(
            modifier = Modifier
                .testTag(stringResource(id = R.string.username_text_field_test_tag))
                .fillMaxWidth(),
            text = username,
            placeholder = stringResource(R.string.username_or_email),
            onValueChange = onUsernameChanged,
            leadingIcon = R.drawable.user
        )
        Spacer(modifier = Modifier.height(31.dp))

        StylishTextField(
            modifier = Modifier
                .testTag(stringResource(id = R.string.password_text_field_test_tag))
                .fillMaxWidth(),
            text = password,
            placeholder = stringResource(R.string.password),
            onValueChange = onPasswordChanged,
            leadingIcon = R.drawable.lock,
            visualTransformation = if (passwordToggled)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            trailingIcon = if (passwordToggled) R.drawable.closed_eye else R.drawable.password,
            trailingIconClicked = {
                passwordToggled = !passwordToggled
            }
        )

        Spacer(modifier = Modifier.height(76.dp))

        StylishButton(
            modifier = Modifier
                .testTag(stringResource(id = R.string.login_button_test_tag))
                .fillMaxWidth(),
            text = stringResource(R.string.login),
            enabled = enabled,
            onClick = onLogin,
            icon = if (!isRunning) null else {
                {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(25.dp)
                            .testTag(stringResource(R.string.login_progress_indicator_test_tag)),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            },
            color = if (enabled)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    StylishTheme {
        LoginRoot(
            username = username,
            password = password,
            onUsernameChanged = { username = it },
            onPasswordChanged = { password = it },
        )
    }
}
