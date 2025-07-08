package com.hoppswap.core.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoppswap.core.common.error.AppException
import com.hoppswap.core.common.state.ResultState
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel(), IntentExecutor {

    fun <A, B> invokeUseCase(
        useCase: BaseUseCase<A, B>,
        args: A,
        onSuccess: (B) -> Unit,
        onFailure: (AppException) -> Unit = {},
        onLoading: () -> Unit = {}
    ) {
        viewModelScope.launch {
            when (val response = useCase.invoke(args)) {
                is ResultState.Success -> onSuccess(response.data)
                is ResultState.Error -> onFailure(response.exception)
                ResultState.Loading -> onLoading()
            }
        }
    }
}

fun interface IntentExecutor {
    fun onIntent(intent: BaseIntent)
}

interface BaseIntent