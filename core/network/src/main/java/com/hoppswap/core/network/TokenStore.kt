package com.hoppswap.core.network

interface TokenStore {

    fun saveSessionToken(token: String)
    fun getSessionToken(): String?
}