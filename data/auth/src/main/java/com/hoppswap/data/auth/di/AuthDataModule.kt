package com.hoppswap.data.auth.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.hoppswap.core.network.TokenStore
import com.hoppswap.data.auth.local.AuthSharedPrefs
import com.hoppswap.data.auth.network.AuthService
import com.hoppswap.data.auth.network.PropertyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthDataModule {

    @Provides
    fun providesAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    fun providesPropertyService(retrofit: Retrofit): PropertyService = retrofit.create(PropertyService::class.java)

    @Provides
    @Singleton
    fun providesTokenStore(authSharedPrefs: AuthSharedPrefs): TokenStore = authSharedPrefs

    @Provides
    @Singleton
    fun providesAuthSharedPrefs(sharedPrefs: SharedPreferences, json: Json): AuthSharedPrefs =
        AuthSharedPrefs(sharedPrefs, json)

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            "user_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}