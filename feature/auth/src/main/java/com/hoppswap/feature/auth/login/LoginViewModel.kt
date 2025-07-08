package com.hoppswap.feature.auth.login

import androidx.lifecycle.viewModelScope
import com.hoppswap.core.common.base.BaseIntent
import com.hoppswap.core.common.base.BaseViewModel
import com.hoppswap.core.common.error.AppException
import com.hoppswap.domain.auth.AttemptLoginArgs
import com.hoppswap.domain.auth.AttemptLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val attemptLoginUseCase: AttemptLoginUseCase
) : BaseViewModel() {

    private val _action = MutableSharedFlow<LoginAction>()
    val action: SharedFlow<LoginAction> = _action

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    override fun onIntent(intent: BaseIntent) {
        when (intent) {
            is LoginIntent.OnLoginSubmitted -> loginSubmitted(intent.email, intent.password)
        }
    }

    private fun loginSubmitted(email: String, password: String) {
        _uiState.update { it.copy(loading = true) }
        if (isValidEmail(email)) {
            invokeUseCase(attemptLoginUseCase, AttemptLoginArgs(email, password), ::onLoginSuccess, ::onLoginFailed)
        } else {
            _uiState.update {
                it.copy(error = LoginError.InvalidEmail, loading = false)
            }
        }
    }

    private fun onLoginSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            viewModelScope.launch {
                _action.emit(LoginAction.NavigateToHomeScreen)
            }
        }
    }

    private fun onLoginFailed(exception: AppException) {
        exception.printStackTrace()
        _uiState.update {
            it.copy(error = LoginError.LoginFailed, loading = false)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matches(email)
    }
}

sealed class LoginError {
    data object InvalidEmail : LoginError()
    data object LoginFailed : LoginError()
}

sealed class LoginAction {
    data object NavigateToHomeScreen : LoginAction()
}

sealed class LoginIntent : BaseIntent {
    data class OnLoginSubmitted(val email: String, val password: String) : LoginIntent()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: LoginError? = null
)