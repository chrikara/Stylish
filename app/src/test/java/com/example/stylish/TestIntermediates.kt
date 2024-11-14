package com.example.stylish

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

internal suspend fun <T> Flow<T>.testFirst(
): T? = coroutineScope {
    var result: T? = null

    val job = launch {
        collect { result = it }
    }
    delay(1L)
    job.cancel()
    return@coroutineScope result
}
