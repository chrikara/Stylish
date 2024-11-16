package com.example.core.presentation.components.modifier

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.semantics

fun Modifier.clickableNoMergeNoRipple(
    onClick: () -> Unit,
): Modifier = semantics(
    mergeDescendants = false,
    properties = {},
).pointerInput(Unit) {
    detectTapGestures(
        onTap = {
            onClick()
        }
    )
}
