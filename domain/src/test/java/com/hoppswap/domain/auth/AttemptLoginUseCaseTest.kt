package com.hoppswap.domain.auth

import com.hoppswap.core.common.error.AppException
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.LoginResponse
import com.hoppswap.data.auth.model.User
import com.hoppswap.data.auth.repo.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AttemptLoginUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var useCase: AttemptLoginUseCase

    @Before
    fun setup() {
        authRepository = mock()
        useCase = AttemptLoginUseCase(authRepository)
    }

    @Test
    fun givenSuccessFromRepository_whenInvoke_thenReturnsSuccessTrue() = runTest {
        // Given
        val args = AttemptLoginArgs("test@example.com", "password")
        val user = User(
            email = args.email,
            name = "Test User",
            city = "Tokyo",
            age = "34",
            bio = "Loves art and food",
            profilePhoto = "https://example.com/photo.jpg",
            successfulSwaps = 10
        )
        val loginResponse = LoginResponse(user, "token")
        whenever(authRepository.attemptLogin(args.email, args.password))
            .thenReturn(ResultState.Success(loginResponse))

        // When
        val result = useCase.invoke(args)

        // Then
        assertEquals(ResultState.Success(true), result)
    }

    @Test
    fun givenNetworkErrorFromRepository_whenInvoke_thenReturnsErrorWithNetworkError() = runTest {
        // Given
        val args = AttemptLoginArgs("test@example.com", "password")
        val ioException = java.io.IOException("No network")
        val exception = AppException.NetworkError(ioException)
        whenever(authRepository.attemptLogin(args.email, args.password))
            .thenReturn(ResultState.Error(exception))

        // When
        val result = useCase.invoke(args)

        // Then
        assertTrue(result is ResultState.Error)
        val error = result as ResultState.Error
        assertTrue(error.exception is AppException.NetworkError)
        assertEquals(ioException, (error.exception as AppException.NetworkError).e)
    }

    @Test
    fun givenHttpErrorFromRepository_whenInvoke_thenReturnsErrorWithHttpError() = runTest {
        // Given
        val args = AttemptLoginArgs("test@example.com", "password")
        val exception = AppException.HttpError(403, "Forbidden")
        whenever(authRepository.attemptLogin(args.email, args.password))
            .thenReturn(ResultState.Error(exception))

        // When
        val result = useCase.invoke(args)

        // Then
        assertTrue(result is ResultState.Error)
        val error = result as ResultState.Error
        assertTrue(error.exception is AppException.HttpError)
        assertEquals(403, (error.exception as AppException.HttpError).code)
        assertEquals("Forbidden", (error.exception as AppException.HttpError).body)
    }

    @Test
    fun givenLoadingFromRepository_whenInvoke_thenReturnsLoading() = runTest {
        // Given
        val args = AttemptLoginArgs("test@example.com", "password")
        whenever(authRepository.attemptLogin(args.email, args.password))
            .thenReturn(ResultState.Loading)

        // When
        val result = useCase.invoke(args)

        // Then
        assertEquals(ResultState.Loading, result)
    }
}