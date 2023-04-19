package com.example.searchgames.ui.search

import com.example.searchgames.data.models.SearchResponse
import javax.inject.Inject

class SearchResultTransformer @Inject constructor() {
    fun transform(searchResponses: SearchResponse): List<SearchResultViewData> {
        return searchResponses.results.mapNotNull {
            it.name?.let { gameName ->
                it.guid?.let { id ->
                    SearchResultViewData(
                        id = id,
                        name = gameName,
                        imageUrl = it.image?.thumbUrl,
                        largeImageUrl = it.image?.screenLargeUrl,
                        deck = it.deck,
                        description = it.description
                    )
                }
            }
        }
    }
}