package com.example.core.commonTest

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.test.SemanticsMatcher
import androidx.test.platform.app.InstrumentationRegistry


private val context: Context
    get() = InstrumentationRegistry.getInstrumentation().context

fun hasTestTag(
    @StringRes testTagId: Int,
): SemanticsMatcher = androidx.compose.ui.test.hasTestTag(
    testTag = context.getString(testTagId)
)

fun hasText(
    @StringRes testTagId: Int,
): SemanticsMatcher = androidx.compose.ui.test.hasText(
    text = context.getString(testTagId)
)

val DrawableId = SemanticsPropertyKey<Int>("DrawableResId")
var SemanticsPropertyReceiver.drawableId by DrawableId

fun hasDrawable(@DrawableRes id: Int): SemanticsMatcher =
    SemanticsMatcher.expectValue(DrawableId, id)
