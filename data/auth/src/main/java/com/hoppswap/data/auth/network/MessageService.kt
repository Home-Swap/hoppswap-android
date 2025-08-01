package com.hoppswap.data.auth.network

import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.model.Message
import retrofit2.http.GET
import retrofit2.http.Path

interface MessageService {

    @GET("messages")
    suspend fun getChats(): List<Chat>

    @GET("messages/{otherUserId}")
    suspend fun getMessages(@Path("otherUserId") otherUserId: String): List<Message>
}