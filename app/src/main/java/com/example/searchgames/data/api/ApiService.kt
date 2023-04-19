package com.example.searchgames.data.api

import com.example.searchgames.data.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("games/?api_key=$API_KEY&format=$JSON_FORMAT&limit=$SEARCH_LIMIT")
    suspend fun searchGames(@Query("filter") filter: String): SearchResponse

    @GET("games/?api_key=$API_KEY&format=$JSON_FORMAT&limit=$SEARCH_LIMIT")
    suspend fun loadMoreGames(@Query("filter") filter: String, @Query("offset") offset: Int): SearchResponse


    companion object {
        private const val API_KEY = "9d45436f87d3848d2bdcce810bacb6df57dfd134"
        private const val JSON_FORMAT = "json"
        private const val SEARCH_LIMIT = 20

        fun buildNameSearchFilter(name: String): String = "name:$name"

    }
}