package com.hoppswap.feature.auth.login

import app.cash.turbine.test
import com.hoppswap.core.common.error.AppException
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.domain.auth.AttemptLoginArgs
import com.hoppswap.domain.auth.AttemptLoginUseCase
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class LoginViewModelTest {

    private lateinit var attemptLoginUseCase: AttemptLoginUseCase
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        attemptLoginUseCase = mock(AttemptLoginUseCase::class.java)
        viewModel = LoginViewModel(attemptLoginUseCase)
    }

    @Test
    fun givenValidEmailAndSuccess_whenLoginSubmitted_thenNavigateToHomeScreen() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val args = AttemptLoginArgs(email, password)
        `when`(attemptLoginUseCase.invoke(args)).thenReturn(ResultState.Success(true))

        // When
        viewModel.action.test {
            viewModel.onIntent(LoginIntent.OnLoginSubmitted(email, password))

            // Then
            // Loading state is set before action emitted
            assertEquals(true, viewModel.uiState.value.loading)
            assertEquals(LoginAction.NavigateToHomeScreen, awaitItem())
        }
    }

    @Test
    fun givenValidEmailAndFailure_whenLoginSubmitted_thenSetLoginFailedError() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val exception = AppException.NetworkError(java.io.IOException("Network down"))
        val args = AttemptLoginArgs(email, password)
        `when`(attemptLoginUseCase.invoke(args)).thenReturn(ResultState.Error(exception))

        // When
        viewModel.onIntent(LoginIntent.OnLoginSubmitted(email, password))

        val error = viewModel.uiState
            .drop(1)
            .first { it.error == LoginError.LoginFailed }
            .error

        // Then
        assertEquals(LoginError.LoginFailed, error)
        assertEquals(false, viewModel.uiState.value.loading)
    }

    @Test
    fun givenInvalidEmail_whenLoginSubmitted_thenSetInvalidEmailError() = runTest {
        // Given
        val invalidEmail = "not-an-email"
        val password = "password123"

        // When
        viewModel.onIntent(LoginIntent.OnLoginSubmitted(invalidEmail, password))

        // Then
        assertEquals(LoginError.InvalidEmail, viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.loading)
    }

    @Test
    fun givenValidEmailAndLoading_whenLoginSubmitted_thenLoadingStateIsSet() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val args = AttemptLoginArgs(email, password)
        `when`(attemptLoginUseCase.invoke(args)).thenReturn(ResultState.Loading)

        // When
        viewModel.onIntent(LoginIntent.OnLoginSubmitted(email, password))

        // Then
        assertEquals(true, viewModel.uiState.value.loading)
    }
}