package com.example.stylish.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.data.di.DispatchersProvider
import com.example.core.data.di.TestStylishDispatchersProvider
import com.example.core.domain.Preferences
import com.example.core.domain.model.UserInfo
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

private const val USER_TEST_PREFERENCES = "UserTestPreferences"

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class],
)
object AppTestModule {
    @Provides
    @Singleton
    fun providesSharedPrefs(
        app: Application
    ): SharedPreferences = app.getSharedPreferences(USER_TEST_PREFERENCES, MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesDispatchers(): DispatchersProvider = TestStylishDispatchersProvider()

    @Provides
    @Singleton
    fun providesPreferences(): Preferences = mockk<Preferences> {
        coEvery { getUserInfo() } returns UserInfo(token = "")
        coEvery { setUserInfo(any()) } returns Unit
    }
}
