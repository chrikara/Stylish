package com.example.stylish.navigationroot

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
import com.example.stylish.R

@Composable
fun SplashRoot(onAnimationFinished: () -> Unit) {
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = Unit) {
        alpha.animateTo(
            1f,
            tween(3000)
        )
        onAnimationFinished()
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

private const val DEFAULT_DURATION_ANIMATION = 3000
private val DEFAULT_LOGO_SIZE = 280.dp
