package com.example.login.data.login.di

import com.example.login.data.login.LoginRepositoryImpl
import com.example.login.data.login.mappers.UserInfoMapper
import com.example.login.domain.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Singleton
    @Provides
    fun provideLoginRepository(): LoginRepository =
        LoginRepositoryImpl(userInfoMapper = UserInfoMapper())
}
