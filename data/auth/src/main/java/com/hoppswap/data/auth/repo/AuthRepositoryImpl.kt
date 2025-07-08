package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.base.BaseRepository
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.local.AuthSharedPrefs
import com.hoppswap.data.auth.model.LoginRequest
import com.hoppswap.data.auth.model.LoginResponse
import com.hoppswap.data.auth.network.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val authSharedPrefs: AuthSharedPrefs
) : BaseRepository(), AuthRepository {

    override suspend fun attemptLogin(email: String, password: String): ResultState<LoginResponse> {
        return safeApiCall {
            val loginResponse = authService.attemptLogin(LoginRequest(email, password))
            authSharedPrefs.saveUser(loginResponse.user)
            loginResponse
        }
    }
}