package com.hoppswap.data.auth.network

import com.hoppswap.data.auth.model.LoginRequest
import com.hoppswap.data.auth.model.LoginResponse
import com.hoppswap.data.auth.model.SignUpRequest
import com.hoppswap.data.auth.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun attemptLogin(@Body loginRequest: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun attemptSignUp(@Body signUpRequest: SignUpRequest): SignUpResponse
}