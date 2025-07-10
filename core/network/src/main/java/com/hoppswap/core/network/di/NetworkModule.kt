package com.hoppswap.core.network.di

import com.hoppswap.core.network.BuildConfig
import com.hoppswap.core.network.HeaderInterceptor
import com.hoppswap.core.network.TokenStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(tokenStore: TokenStore): HeaderInterceptor =
        HeaderInterceptor(tokenStore)

    @Provides
    @Singleton
    fun providesOkHttp(headerInterceptor: HeaderInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    },
            )
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BuildConfig.BACKEND_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}