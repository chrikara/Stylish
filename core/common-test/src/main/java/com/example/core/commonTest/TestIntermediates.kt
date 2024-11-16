package com.example.core.commonTest

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

suspend fun <T> Flow<T>.testFirst(
): T? = coroutineScope {
    var result: T? = null

    val job = launch {
        collect { result = it }
    }
    delay(10000L)
    job.cancel()
    return@coroutineScope result
}
