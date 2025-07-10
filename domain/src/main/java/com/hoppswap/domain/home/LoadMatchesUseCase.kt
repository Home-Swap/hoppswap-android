package com.hoppswap.domain.home

import com.hoppswap.core.common.base.BaseUseCase
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Property
import com.hoppswap.data.auth.repo.PropertyRepository
import javax.inject.Inject

class LoadMatchesUseCase @Inject constructor(private val propertyRepository: PropertyRepository) :
    BaseUseCase<Unit, List<Property>> {

    override suspend fun invoke(args: Unit): ResultState<List<Property>> = propertyRepository.getMatches()
}