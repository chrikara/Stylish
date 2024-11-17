package com.example.stylish.di

import com.example.core.domain.model.UserInfo
import com.example.login.data.login.di.LoginModule
import com.example.login.domain.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [LoginModule::class],
)
object LoginTestModule {
    @Singleton
    @Provides
    fun provideLoginRepository(): LoginRepository = object : LoginRepository {
        override suspend fun login(username: String, password: String): Result<UserInfo> =
            Result.success(UserInfo())

        /*
        Known issue -> https://github.com/mockk/mockk/issues/485
        So, instead of using mocks (sadly), on way is to use anonymous objects.
         */

    }
}
