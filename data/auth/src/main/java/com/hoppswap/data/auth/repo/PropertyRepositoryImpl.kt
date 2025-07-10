package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.base.BaseRepository
import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Property
import com.hoppswap.data.auth.network.PropertyService
import javax.inject.Inject

class PropertyRepositoryImpl @Inject constructor(private val propertyService: PropertyService) : BaseRepository(),
    PropertyRepository {

    override suspend fun getMatches(): ResultState<List<Property>> = safeApiCall {
        propertyService.getMatches()
    }
}