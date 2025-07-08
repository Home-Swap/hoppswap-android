package com.hoppswap

import androidx.lifecycle.viewModelScope
import com.hoppswap.core.common.base.BaseIntent
import com.hoppswap.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BaseViewModel() {

    private val _uiState = MutableStateFlow(MainUiState(true))
    val uiState = _uiState.asStateFlow()

    override fun onIntent(intent: BaseIntent) {
        when (intent) {
            is MainIntent.StartApplication -> triggerStartTimer()
        }
    }

    private fun triggerStartTimer() {
        viewModelScope.launch {
            delay(3000)
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}

data class MainUiState(
    val isLoading: Boolean
)

sealed class MainIntent : BaseIntent {
    data object StartApplication: MainIntent()
}