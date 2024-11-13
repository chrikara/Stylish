package com.example.stylish.ui.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun onUsernameChanged(username: String) {
        _username.update { username }
    }

    fun onPasswordChanged(password: String) {
        _password.update { password }
    }

    val enabled = combine(
        username, password,
    ) { username, password ->
        // Example validation (Could be anything)
        if (username.length >= MAX_USERNAME_CHARS && password.length >= MAX_PASSWORD_CHARS)
            true
        else
            false
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = false,
    )

    companion object {
        const val MAX_USERNAME_CHARS = 3
        const val MAX_PASSWORD_CHARS = 8
    }
}
