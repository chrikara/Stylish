package com.example.core.network

interface FakeStoreApi : Api {
    override val baseUrl: String
        get() = "https://fakestoreapi.com"
}
