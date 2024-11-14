package com.example.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json


fun Api.createHttpClient(
    baseUrl: String,
) = HttpClient {
    defaultRequest {
        contentType(ContentType.Application.Json)
        url(baseUrl)
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 60_000
    }


    install(Logging) {
        level = LogLevel.ALL
    }
}
