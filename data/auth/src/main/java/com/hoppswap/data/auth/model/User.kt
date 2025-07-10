package com.hoppswap.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val city: String,
    val age: String,
    val bio: String,
    val profilePhoto: String,
    val successfulSwaps: Int
)