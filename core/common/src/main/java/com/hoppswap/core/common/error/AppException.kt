package com.hoppswap.core.common.error

import java.io.IOException
import java.net.SocketTimeoutException

sealed class AppException(throwable: Throwable? = null): Exception(throwable) {
    data class NetworkError(val e: IOException) : AppException(e)
    data class TimeoutError(val e: SocketTimeoutException) : AppException(e)
    data class HttpError(val code: Int, val body: String?) : AppException()
    data class UnknownError(val e: Exception? = null) : AppException(e)
}
