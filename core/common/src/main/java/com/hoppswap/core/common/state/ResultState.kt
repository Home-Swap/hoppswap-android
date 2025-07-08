package com.hoppswap.core.common.state

import com.hoppswap.core.common.error.AppException

sealed class ResultState<out T> {
    data class Success<T>(val data: T): ResultState<T>()
    data class Error(val exception: AppException): ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}
