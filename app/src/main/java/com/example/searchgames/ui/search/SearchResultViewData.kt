package com.example.searchgames.ui.search

data class SearchResultViewData(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val largeImageUrl: String?,
    val deck: String?,
    val description: String?,
)