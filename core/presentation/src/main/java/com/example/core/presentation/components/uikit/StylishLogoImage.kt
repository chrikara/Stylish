package com.example.core.presentation.components.uikit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.core.presentation.R

@Composable
fun StylishLogoImage(
    modifier: Modifier = Modifier,
    @DrawableRes resId : Int = R.drawable.stylish_logo,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = resId),
        contentDescription = "logo",
    )
}
