package com.example.searchgames.di

import com.example.searchgames.api.RetrofitBuilder
import com.example.searchgames.data.api.ApiService
import com.example.searchgames.data.repository.SearchRepository
import com.example.searchgames.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return RetrofitBuilder().apiService
    }

    @Provides
    @Singleton
    fun provideSearchRepository(apiService: ApiService, dispatchProvider: DispatcherProvider) =
        SearchRepository(apiService, dispatchProvider)
}