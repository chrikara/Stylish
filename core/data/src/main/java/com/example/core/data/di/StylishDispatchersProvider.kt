package com.example.core.data.di

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class StylishDispatchersProvider @Inject constructor() : DispatchersProvider {
    override val main: CoroutineContext = Dispatchers.Main
    override val io: CoroutineContext = Dispatchers.IO
    override val default: CoroutineContext = Dispatchers.Default
}

class TestStylishDispatchersProvider @Inject constructor() : DispatchersProvider {
    override val main: CoroutineContext = Dispatchers.Unconfined
    override val io: CoroutineContext = Dispatchers.Unconfined
    override val default: CoroutineContext = Dispatchers.Unconfined
}
