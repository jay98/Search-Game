package com.example.searchgames.data.models

import com.google.gson.annotations.SerializedName


data class GameDetail(

    @SerializedName("aliases")
    var aliases: String? = null,

    @SerializedName("api_detail_url")
    var apiDetailUrl: String? = null,

    @SerializedName("date_added")
    var dateAdded: String? = null,

    @SerializedName("date_last_updated")
    var dateLastUpdated: String? = null,

    @SerializedName("deck")
    var deck: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("expected_release_day")
    var expectedReleaseDay: String? = null,

    @SerializedName("expected_release_month")
    var expectedReleaseMonth: String? = null,

    @SerializedName("expected_release_quarter")
    var expectedReleaseQuarter: String? = null,

    @SerializedName("expected_release_year")
    var expectedReleaseYear: String? = null,

    @SerializedName("guid")
    var guid: String? = null,

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("image")
    var image: Image? = Image(),

    @SerializedName("name")
    var name: String? = null

)