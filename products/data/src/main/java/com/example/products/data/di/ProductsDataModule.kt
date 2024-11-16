package com.example.products.data.di

import com.example.products.data.ProductsRepositoryImpl
import com.example.products.domain.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductsDataModule {

    @Singleton
    @Provides
    fun provideLoginRepository(): ProductsRepository =
        ProductsRepositoryImpl()

}
