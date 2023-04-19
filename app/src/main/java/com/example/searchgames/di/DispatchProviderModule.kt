package com.example.searchgames.di

import com.example.searchgames.util.DefaultDispatcherProvider
import com.example.searchgames.util.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DispatchProviderModule {

    @Singleton
    @Binds
    abstract fun bindDispatchProvider(impl: DefaultDispatcherProvider): DispatcherProvider
}