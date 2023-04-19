package com.example.searchgames.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("error") var error: String? = null,
    @SerializedName("limit") var limit: Int,
    @SerializedName("offset") var offset: Int,
    @SerializedName("number_of_page_results") var numberOfPageResults: Int,
    @SerializedName("number_of_total_results") var numberOfTotalResults: Int,
    @SerializedName("status_code") var statusCode: Int? = null,
    @SerializedName("results") var results: ArrayList<GameDetail> = arrayListOf(),
    @SerializedName("version") var version: String? = null
)