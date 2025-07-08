package com.hoppswap.data.auth.di

import com.hoppswap.data.auth.repo.AuthRepository
import com.hoppswap.data.auth.repo.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthRepoModule {

    @Binds
    abstract fun providesAuthRepo(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}