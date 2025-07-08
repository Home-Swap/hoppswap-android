package com.hoppswap.core.common.base

import com.hoppswap.core.common.state.ResultState

fun interface BaseUseCase<A, B> {

    suspend fun invoke(args: A): ResultState<B>
}