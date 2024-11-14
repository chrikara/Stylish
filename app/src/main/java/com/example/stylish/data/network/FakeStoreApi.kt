package com.example.stylish.data.network

interface FakeStoreApi : Api {
    override val baseUrl: String
        get() = "https://fakestoreapi.com"
}
