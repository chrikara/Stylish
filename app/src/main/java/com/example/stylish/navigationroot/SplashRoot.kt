package com.example.stylish.navigationroot

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stylish.R

@Composable
fun SplashRoot(
    viewModel: SplashViewModel = hiltViewModel(),
    onAnimationFinished: (isLoggedIn: Boolean) -> Unit,
) {
    BackHandler {}

    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = Unit) {
        alpha.animateTo(
            1f,
            tween(DEFAULT_DURATION_ANIMATION)
        )
        onAnimationFinished(viewModel.isLoggedIn())
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier
                .size(
                    width = 280.dp,
                    height = 100.dp,
                )
                .alpha(alpha.value),
            painter = painterResource(id = R.drawable.stylish_logo),
            contentDescription = ""
        )
    }
}

private const val DEFAULT_DURATION_ANIMATION = 2500
