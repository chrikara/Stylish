package com.example.stylish.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.data.DefaultPreferences
import com.example.core.data.di.DispatchersProvider
import com.example.core.data.di.StylishDispatchersProvider
import com.example.core.domain.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "UserPreferences"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesSharedPrefs(
        app: Application
    ): SharedPreferences = app.getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)

    @Provides
    @Singleton
    fun providesDispatchers(): DispatchersProvider = StylishDispatchersProvider()

    @Provides
    @Singleton
    fun providesPreferences(
        sharedPrefs: SharedPreferences,
        dispatchersProvider: DispatchersProvider,
    ): Preferences = DefaultPreferences(sharedPrefs, dispatchersProvider)
}
