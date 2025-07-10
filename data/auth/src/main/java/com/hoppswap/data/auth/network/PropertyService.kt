package com.hoppswap.data.auth.network

import com.hoppswap.data.auth.model.Property
import retrofit2.http.GET

interface PropertyService {

    @GET("user/matches")
    suspend fun getMatches(): List<Property>
}