package com.example.searchgames

import com.example.searchgames.data.models.GameDetail
import com.example.searchgames.data.models.Image
import com.example.searchgames.data.models.SearchResponse
import com.example.searchgames.ui.search.SearchResultViewData

object MockData {

    val searchResponse = SearchResponse(
        limit = 1,
        offset = 1,
        numberOfPageResults = 1,
        numberOfTotalResults = 12,
        results = arrayListOf(
            GameDetail(
                guid = "guid",
                name = "name",
                image = Image(
                    thumbUrl = "thumbUrl",
                    screenLargeUrl = "screenLargeUrl"
                ),
                deck = "deck",
                description = "description"
            )
        )
    )

    val searchResultViewData = SearchResultViewData(
        id = "guid",
        name = "name",
        imageUrl = "thumbUrl",
        largeImageUrl = "screenLargeUrl",
        deck = "deck",
        description = "description"
    )

    val validGameDetail1 = GameDetail(
        name = "Game 1",
        guid = "1",
        image = Image(thumbUrl = "thumb1", screenLargeUrl = "large1"),
        deck = "Deck 1",
        description = "Description 1"
    )

    val validGameDetail2 = GameDetail(
        name = "Game 2",
        guid = "2",
        image = Image(thumbUrl = "thumb2", screenLargeUrl = "large2"),
        deck = "Deck 2",
        description = "Description 2"
    )

    val invalidGameDetailNoName =  GameDetail(
        guid = "2",
        image = Image(thumbUrl = "thumb2", screenLargeUrl = "large2"),
        deck = "Deck 2",
        description = "Description 2"
    )

    val invalidGameDetailNoGuid = GameDetail(
        name = "Game 3",
        image = Image(thumbUrl = "thumb3", screenLargeUrl = "large3"),
        deck = "Deck 3",
        description = "Description 3"
    )


}
