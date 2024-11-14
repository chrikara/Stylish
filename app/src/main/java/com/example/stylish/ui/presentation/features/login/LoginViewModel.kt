package com.example.stylish.ui.presentation.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stylish.domain.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
) : ViewModel() {

    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    fun onUsernameChanged(username: String) {
        _username.update { username }
    }

    fun onPasswordChanged(password: String) {
        _password.update { password }
    }

    fun nextClicked(
        onSuccess: (String) -> Unit,
        onShowErrorMessage: () -> Unit,
    ) {
        viewModelScope.launch {
            _isRunning.update { true }

            /*
            Delay is just to simulate a longer call
            for UI purposes (hurrayyy)
             */

            delay(1000L)
            val result = loginRepository.login(
                username = username.value.trim(),
                password = password.value.trim(),
            )
            _isRunning.update { false }

            result.onSuccess {
                onSuccess(it)
            }.onFailure {
                onShowErrorMessage()
            }
        }
    }

    val enabled = combine(
        username, password, isRunning
    ) { username, password, isRunning ->
        // Example validation (Could be anything)
        if (username.length >= MAX_USERNAME_CHARS && password.length >= MAX_PASSWORD_CHARS && !isRunning)
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
        const val MAX_PASSWORD_CHARS = 3
    }
}
