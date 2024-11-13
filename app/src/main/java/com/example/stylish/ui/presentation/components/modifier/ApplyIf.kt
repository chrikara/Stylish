package com.example.stylish.ui.presentation.components.modifier

import androidx.compose.ui.Modifier

fun Modifier.applyIf(
    enabled: Boolean,
    modifier: Modifier,
): Modifier {
    return if (enabled) {
        then(modifier)
    } else {
        this
    }
}
