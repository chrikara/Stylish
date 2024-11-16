package com.example.core.data.di

import kotlin.coroutines.CoroutineContext

interface DispatchersProvider {
    val main: CoroutineContext
    val io: CoroutineContext
    val default: CoroutineContext
}
