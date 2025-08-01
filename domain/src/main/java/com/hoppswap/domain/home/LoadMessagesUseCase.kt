package com.hoppswap.domain.home

import com.hoppswap.core.common.base.BaseUseCase
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Message
import com.hoppswap.data.auth.repo.MessageRepository
import javax.inject.Inject

class LoadMessagesUseCase @Inject constructor(private val messageRepository: MessageRepository) :
    BaseUseCase<String, List<Message>> {

    override suspend fun invoke(args: String): ResultState<List<Message>> = messageRepository.getMessages(args)
}