package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.base.BaseRepository
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.model.Message
import com.hoppswap.data.auth.network.MessageService
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val messageService: MessageService
) : BaseRepository(), MessageRepository {

    override suspend fun getChats(): ResultState<List<Chat>> = safeApiCall {
        messageService.getChats()
    }

    override suspend fun getMessages(otherUserId: String): ResultState<List<Message>> = safeApiCall {
        messageService.getMessages(otherUserId)
    }
}