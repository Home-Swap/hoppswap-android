package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.LoginResponse

interface AuthRepository {

    suspend fun attemptLogin(email: String, password: String): ResultState<LoginResponse>
}