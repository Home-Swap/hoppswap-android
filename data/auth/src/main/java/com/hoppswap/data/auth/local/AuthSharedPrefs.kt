package com.hoppswap.data.auth.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.hoppswap.core.network.TokenStore
import com.hoppswap.data.auth.model.User
import kotlinx.serialization.json.Json

private const val KEY_USER = "user"
private const val KEY_TOKEN = "session_token"

class AuthSharedPrefs(
    private val sharedPreferences: SharedPreferences,
    private val json: Json
): TokenStore {

    fun saveUser(user: User) {
        val jsonString = json.encodeToString(user)
        sharedPreferences.edit {
            putString(KEY_USER, jsonString)
        }
    }

    fun getUser(): User? {
        val jsonString = sharedPreferences.getString(KEY_USER, null) ?: return null
        return try {
            json.decodeFromString<User>(jsonString)
        } catch (e: Exception) {
            null
        }
    }

    override fun saveSessionToken(token: String) {
        sharedPreferences.edit { putString(KEY_TOKEN, token) }
    }

    override fun getSessionToken(): String? = sharedPreferences.getString(KEY_TOKEN, null)

    fun logout() {
        sharedPreferences.edit {
            remove(KEY_USER)
            remove(KEY_TOKEN)
        }
    }
}