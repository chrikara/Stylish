package com.example.core.presentation.components.toast

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.StringRes

private var toast: Toast? = null

fun Context.showSingleToast(
    @StringRes stringId: Int,
    duration: Int = LENGTH_LONG,
) {
    toast?.cancel()
    toast = Toast.makeText(this, stringId, duration).also { it.show() }
}
