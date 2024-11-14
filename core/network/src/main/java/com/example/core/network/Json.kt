package com.example.core.network

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json: Json by lazy {
    Json {
        ignoreUnknownKeys = true
    }
}

inline fun <reified T : Any> T.toJson(): String =
    json.encodeToString(this)

inline fun <reified T : Any> String.fromJson(): T =
    json.decodeFromString(this)
