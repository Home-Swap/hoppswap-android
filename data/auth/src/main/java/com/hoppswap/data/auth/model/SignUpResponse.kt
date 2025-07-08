package com.hoppswap.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val user: User,
    val accessToken: String
)
