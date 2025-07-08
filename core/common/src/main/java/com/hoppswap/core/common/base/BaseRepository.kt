package com.hoppswap.core.common.base

import com.hoppswap.core.common.error.AppException
import com.hoppswap.core.common.state.ResultState
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultState<T> {
        return try {
            val result = apiCall()
            ResultState.Success(result)
        } catch (e: IOException) {
            ResultState.Error(AppException.NetworkError(e))
        } catch (e: HttpException) {
            val code = e.code()
            val errorBody = e.response()?.errorBody()?.string()
            ResultState.Error(AppException.HttpError(code, errorBody))
        } catch (e: SocketTimeoutException) {
            ResultState.Error(AppException.TimeoutError(e))
        } catch (e: Exception) {
            ResultState.Error(AppException.UnknownError(e))
        }
    }
}