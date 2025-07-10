package com.hoppswap.feature.home

import androidx.lifecycle.viewModelScope
import com.hoppswap.core.common.base.BaseIntent
import com.hoppswap.core.common.base.BaseViewModel
import com.hoppswap.core.common.error.AppException
import com.hoppswap.data.auth.model.Property
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
class HomeViewModel @Inject constructor(private val loadMatchesUseCase: LoadMatchesUseCase) : BaseViewModel() {

    private val _action = MutableSharedFlow<HomeAction>()
    val action: SharedFlow<HomeAction> = _action

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    override fun onIntent(intent: BaseIntent) {
        when (intent) {
            is HomeIntent.OnLoadMatches -> onLoadMatches()
            is HomeIntent.OnMatchClicked -> onMatchClicked(intent.property)
        }
    }

    private fun onLoadMatches() {
        invokeUseCase(loadMatchesUseCase, Unit, ::onLoadSuccess, ::onLoadFailed)
    }

    private fun onLoadSuccess(properties: List<Property>) {
        _uiState.update {
            it.copy(
                matches = properties,
                loading = false
            )
        }
    }

    private fun onLoadFailed(exception: AppException) {
        exception.printStackTrace()
        _uiState.update {
            it.copy(
                error = HomeError.RetrieveMatchesError,
                loading = false,
            )
        }
    }

    private fun onMatchClicked(property: Property) {
        viewModelScope.launch(Dispatchers.Main) {
            _action.emit(HomeAction.NavigateToPropertyDetails(property))
        }
    }
}

data class HomeUiState(
    val matches: List<Property> = emptyList(),
    val loading: Boolean = true,
    val error: HomeError? = null
)

sealed class HomeError {
    data object RetrieveMatchesError : HomeError()
}

sealed class HomeAction {
    data class NavigateToPropertyDetails(val property: Property) : HomeAction()
}

sealed class HomeIntent : BaseIntent {
    data object OnLoadMatches : HomeIntent()
    data class OnMatchClicked(val property: Property) : HomeIntent()
}