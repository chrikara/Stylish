package com.example.stylish.navigationroot

import androidx.lifecycle.ViewModel
import com.example.core.domain.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preferences: Preferences,
) : ViewModel() {


    suspend fun isLoggedIn() =
        preferences.getUserInfo().let { userInfo ->
            userInfo != null && userInfo.token.isNotBlank()
        }

}
