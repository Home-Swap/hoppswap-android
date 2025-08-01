package com.hoppswap.domain.home

import com.hoppswap.core.common.base.BaseUseCase
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.repo.MessageRepository
import javax.inject.Inject

class LoadChatsUseCase @Inject constructor(private val messageRepository: MessageRepository) :
    BaseUseCase<Unit, List<Chat>> {

    override suspend fun invoke(args: Unit): ResultState<List<Chat>> = messageRepository.getChats()
}