package com.muasdev.simpleandroidmvvm.di

import com.muasdev.simpleandroidmvvm.data.repository.PlaceholderRepositoryImpl
import com.muasdev.simpleandroidmvvm.data.repository.PreferenceHelperImpl
import com.muasdev.simpleandroidmvvm.data.repository.UserRepositoryImpl
import com.muasdev.simpleandroidmvvm.domain.repository.PlaceholderRepository
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(preferenceHelperImpl: PreferenceHelperImpl): PreferenceHelper

    @Binds
    @Singleton
    abstract fun bindPlaceholderRepository(placeholderRepositoryImpl: PlaceholderRepositoryImpl): PlaceholderRepository

}