package com.hoppswap.core.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val tokenStore: TokenStore) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenStore.getSessionToken()
        Log.e("HEADER", "$token")
        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        return chain.proceed(requestBuilder.build())
    }
}