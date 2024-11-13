package com.example.stylish.ui.presentation.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stylish.R
import com.example.stylish.ui.presentation.components.StylishTextField


@Composable
fun LoginRoot(
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val username by viewModel.username.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()

    LoginRoot(
        username = username,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        password = password,
        enabled = enabled,
        isRunning = false,
        onLogin = {
            // Todo
            onSuccessfulLogin()
        }
    )
}

@Composable
fun LoginRoot(
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
        var toggled by remember {
            mutableStateOf(false)
        }

        val progressIndicator: @Composable () -> Unit = {
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Spacer(modifier = Modifier.height(31.dp))

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(31.dp))

        StylishTextField(
            text = username,
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.username_or_email),
            onValueChange = onUsernameChanged,
            leadingIcon = R.drawable.user
        )
        Spacer(modifier = Modifier.height(31.dp))

        StylishTextField(
            modifier = Modifier.fillMaxWidth(),
            text = password,
            placeholder = stringResource(R.string.username_or_email),
            onValueChange = onPasswordChanged,
            leadingIcon = R.drawable.lock,
            visualTransformation = if (toggled)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            trailingIcon = if (toggled) R.drawable.closed_eye else R.drawable.password,
            trailingIconClicked = {
                toggled = !toggled
            }
        )

        Spacer(modifier = Modifier.height(76.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            text = "Login",
            enabled = enabled,
            onClick = onLogin,
            icon = if (!isRunning) null else {
                {
                    progressIndicator()

                }
            },
            textColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.titleLarge,
            color = if (enabled)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(4.dp)
        )
    }
}

@Composable
fun Button(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textColor: Color,
    textStyle: TextStyle,
    icon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    color: Color,
    shape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 0.dp,
        vertical = 0.dp,
    ),
) {

    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = color,
                shape = shape
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
            .width(IntrinsicSize.Max)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            if (icon != null) {
                icon.invoke()

                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = text,
                textAlign = TextAlign.Center,
                color = textColor,
                style = textStyle,
            )
        }
    }
}
