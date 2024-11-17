package com.example.stylish.di

import com.example.products.data.di.ProductsModule
import com.example.products.domain.ProductsRepository
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import com.example.products.domain.model.fakeCategories
import com.example.products.domain.model.fakeProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ProductsModule::class],
)
object ProductsTestModule {
    @Singleton
    @Provides
    fun provideProductsRepository(): ProductsRepository = object : ProductsRepository {
        override suspend fun getCategories(): Result<List<Category>> =
            Result.success(fakeCategories)

        override suspend fun getProducts(): Result<List<Product>> = Result.success(fakeProducts)

        override suspend fun getProduct(id: Int): Result<Product> = Result.success(fakeProducts[0])

        override suspend fun updateProduct(
            id: Int,
            title: String,
            price: Double,
            description: String,
            category: String
        ): Result<Unit> = Result.success(Unit)
    }
}
