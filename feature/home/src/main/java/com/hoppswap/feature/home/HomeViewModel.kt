package com.hoppswap.feature.home

import androidx.lifecycle.viewModelScope
import com.hoppswap.core.common.base.BaseIntent
import com.hoppswap.core.common.base.BaseViewModel
import com.hoppswap.core.common.error.AppException
import com.hoppswap.data.auth.model.Chat
import com.hoppswap.data.auth.model.Property
import com.hoppswap.domain.home.LoadChatsUseCase
import com.hoppswap.domain.home.LoadMatchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadMatchesUseCase: LoadMatchesUseCase,
    private val loadChatsUseCase: LoadChatsUseCase
) : BaseViewModel() {

    private val _action = MutableSharedFlow<HomeAction>()
    val action: SharedFlow<HomeAction> = _action

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    override fun onIntent(intent: BaseIntent) {
        when (intent) {
            is HomeIntent.OnLoadMatches -> onLoadMatches()
            is HomeIntent.OnMatchClicked -> onMatchClicked(intent.property)
            is HomeIntent.OnChatClicked -> onChatClicked(intent.chat)
        }
    }

    private fun onLoadMatches() {
        invokeUseCase(loadMatchesUseCase, Unit, ::onLoadMatchesSuccess, ::onLoadMatchesFailed)
        invokeUseCase(loadChatsUseCase, Unit, ::onLoadChatsSuccess, ::onLoadChatsFailed)
    }

    private fun onLoadMatchesSuccess(properties: List<Property>) {
        _uiState.update {
            it.copy(
                matches = properties,
                matchesLoading = false
            )
        }
    }

    private fun onLoadMatchesFailed(exception: AppException) {
        exception.printStackTrace()
        _uiState.update {
            it.copy(
                error = HomeError.RetrieveMatchesError,
                matchesLoading = false,
            )
        }
    }

    private fun onLoadChatsSuccess(chats: List<Chat>) {
        _uiState.update {
            it.copy(
                chats = chats,
                chatsLoading = false
            )
        }
    }

    private fun onLoadChatsFailed(exception: AppException) {
        exception.printStackTrace()
        _uiState.update {
            it.copy(
                error = HomeError.RetrieveChatsError,
                chatsLoading = false,
            )
        }
    }

    private fun onMatchClicked(property: Property) {
        viewModelScope.launch(Dispatchers.Main) {
            _action.emit(HomeAction.NavigateToPropertyDetails(property))
        }
    }

    private fun onChatClicked(chat: Chat) {
        viewModelScope.launch(Dispatchers.Main) {
            _action.emit(HomeAction.NavigateToChatPage(chat))
        }
    }
}

data class HomeUiState(
    val matches: List<Property> = emptyList(),
    val chats: List<Chat> = emptyList(),
    val matchesLoading: Boolean = true,
    val chatsLoading: Boolean = true,
    val error: HomeError? = null
)

sealed class HomeError {
    data object RetrieveMatchesError : HomeError()
    data object RetrieveChatsError : HomeError()
}

sealed class HomeAction {
    data class NavigateToPropertyDetails(val property: Property) : HomeAction()
    data class NavigateToChatPage(val chat: Chat) : HomeAction()
}

sealed class HomeIntent : BaseIntent {
    data object OnLoadMatches : HomeIntent()
    data class OnMatchClicked(val property: Property) : HomeIntent()
    data class OnChatClicked(val chat: Chat) : HomeIntent()
}