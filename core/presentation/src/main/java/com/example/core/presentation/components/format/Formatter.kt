package com.example.core.presentation.components.format

import java.text.NumberFormat
import java.util.Locale

fun Number.format(): String = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
