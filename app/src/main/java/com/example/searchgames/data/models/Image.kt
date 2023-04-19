package com.example.searchgames.data.models

import com.google.gson.annotations.SerializedName


data class Image(

    @SerializedName("icon_url") var iconUrl: String? = null,
    @SerializedName("medium_url") var mediumUrl: String? = null,
    @SerializedName("screen_url") var screenUrl: String? = null,
    @SerializedName("screen_large_url") var screenLargeUrl: String? = null,
    @SerializedName("small_url") var smallUrl: String? = null,
    @SerializedName("super_url") var superUrl: String? = null,
    @SerializedName("thumb_url") var thumbUrl: String? = null,
    @SerializedName("tiny_url") var tinyUrl: String? = null,
    @SerializedName("original_url") var originalUrl: String? = null,
    @SerializedName("image_tags") var imageTags: String? = null

)