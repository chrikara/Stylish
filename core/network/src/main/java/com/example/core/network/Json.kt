package com.example.core.network

import kotlinx.serialization.json.Json

val json: Json by lazy {
    Json {
        ignoreUnknownKeys = true
    }
}
