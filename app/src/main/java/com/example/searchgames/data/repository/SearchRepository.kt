package com.example.searchgames.data.repository

import com.example.searchgames.api.RetrofitBuilder
import com.example.searchgames.data.api.ApiService
import com.example.searchgames.util.DispatcherProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: DispatcherProvider,
) {

    fun searchGames(searchTerm: String) = flow {
        emit(apiService.searchGames(ApiService.buildNameSearchFilter(searchTerm)))
    }.flowOn(dispatcherProvider.io)

    fun loadMoreGames(searchTerm: String, offset: Int) = flow {
        emit(apiService.loadMoreGames(ApiService.buildNameSearchFilter(searchTerm), offset))
    }.flowOn(dispatcherProvider.io)
}