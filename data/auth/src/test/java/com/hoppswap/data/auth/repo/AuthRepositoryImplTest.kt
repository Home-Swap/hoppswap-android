package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.local.AuthSharedPrefs
import com.hoppswap.data.auth.model.LoginRequest
import com.hoppswap.data.auth.model.LoginResponse
import com.hoppswap.data.auth.model.User
import com.hoppswap.data.auth.network.AuthService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class AuthRepositoryImplTest {

    private lateinit var authService: AuthService
    private lateinit var authSharedPrefs: AuthSharedPrefs
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setup() {
        authService = mock()
        authSharedPrefs = mock()
        authRepository = AuthRepositoryImpl(authService, authSharedPrefs)
    }

    @Test
    fun givenValidCredentials_whenAttemptLogin_thenReturnSuccessAndSaveUser() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val user = User(
            email = email,
            name = "Test User",
            city = "Tokyo",
            age = "34",
            bio = "Loves art and food",
            profilePhoto = "https://example.com/photo.jpg",
            successfulSwaps = 10
        )
        val accessToken = "someAccessToken123"
        val loginResponse = LoginResponse(user, accessToken)
        whenever(authService.attemptLogin(LoginRequest(email, password))).thenReturn(loginResponse)

        // When
        val result = authRepository.attemptLogin(email, password)

        // Then
        verify(authSharedPrefs).saveUser(user)
        assertEquals(ResultState.Success(loginResponse), result)
    }

    @Test
    fun givenServiceError_whenAttemptLogin_thenReturnErrorAndNotSaveUser() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val exception = RuntimeException("Network error")
        whenever(authService.attemptLogin(LoginRequest(email, password))).thenThrow(exception)

        // When
        val result = authRepository.attemptLogin(email, password)

        // Then
        verify(authSharedPrefs, never()).saveUser(any())
        assertTrue(result is ResultState.Error)
    }
}