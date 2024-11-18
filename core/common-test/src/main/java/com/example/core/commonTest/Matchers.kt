package com.example.core.commonTest

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.hasText
import androidx.test.platform.app.InstrumentationRegistry
import com.example.core.presentation.components.drawable.DrawableId


private val context: Context
    get() = InstrumentationRegistry.getInstrumentation().context

fun hasTestTag(
    @StringRes testTagId: Int,
): SemanticsMatcher = androidx.compose.ui.test.hasTestTag(
    testTag = context.getString(testTagId)
)

fun hasText(
    @StringRes testTagId: Int,
): SemanticsMatcher = hasText(
    text = context.getString(testTagId)
)

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)
