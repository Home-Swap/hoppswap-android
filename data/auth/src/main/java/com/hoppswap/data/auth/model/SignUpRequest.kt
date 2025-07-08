package com.hoppswap.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val age: String,
    val bio: String,
)
