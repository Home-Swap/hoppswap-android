package com.hoppswap.data.auth.repo

import com.hoppswap.core.common.state.ResultState
import com.hoppswap.data.auth.model.Property

interface PropertyRepository {

    suspend fun getMatches(): ResultState<List<Property>>
}