package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.model.Message

interface MessageRepository {

    suspend fun getChats(): ResultState<List<Chat>>

    suspend fun getMessages(otherUserId: String): ResultState<List<Message>>
}