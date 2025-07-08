package com.hoppswap.domain.auth

import com.hoppswap.core.common.base.BaseUseCase
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.repo.AuthRepository
import javax.inject.Inject

class AttemptLoginUseCase @Inject constructor(private val authRepository: AuthRepository) :
    BaseUseCase<AttemptLoginArgs, Boolean> {

    override suspend fun invoke(args: AttemptLoginArgs): ResultState<Boolean> {
        return when (val result = authRepository.attemptLogin(args.email, args.password)) {
            is ResultState.Success -> ResultState.Success(true)
            is ResultState.Error -> ResultState.Error(result.exception)
            is ResultState.Loading -> ResultState.Loading
        }
    }
}

data class AttemptLoginArgs(
    val email: String,
    val password: String,
)