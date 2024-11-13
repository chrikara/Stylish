package com.example.stylish.data.network

import com.example.stylish.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json


fun Api.createHttpClient(
    baseUrl: String,
) = HttpClient {
    expectSuccess = true
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
