package com.hoppswap.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val user: User,
    val accessToken: String
)
