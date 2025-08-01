package com.hoppswap.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val chatId: String,
    val otherUserId: String,
    val createdAt: String,
    val text: String,
    val name: String,
    val profilePhoto: String,
    val propertyPhoto: String
)

@Serializable
data class Message(
    val chatId: String,
    val sender: String,
    val receiver: String,
    val text: String,
    val read: Boolean,
    val createdAt: String,
    val type: String,
)
