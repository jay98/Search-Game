package com.example.searchgames

import com.example.searchgames.ui.search.SearchResultTransformer
import com.example.searchgames.ui.search.SearchResultViewData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchResultTransformerTest {

    private lateinit var transformer: SearchResultTransformer

    @Before
    fun setUp() {
        transformer = SearchResultTransformer()
    }

    @Test
    fun `transform should return a list of SearchResultViewData`() {

        val searchResponse = MockData.searchResponse.copy(
            results = arrayListOf(
                MockData.validGameDetail1,
                MockData.validGameDetail2
            )
        )

        val result = transformer.transform(searchResponse)

        assertEquals(2, result.size)
        assertEquals(
            SearchResultViewData(
                "1",
                "Game 1",
                "thumb1",
                "large1",
                "Deck 1",
                "Description 1"
            ), result[0]
        )
        assertEquals(
            SearchResultViewData(
                "2",
                "Game 2",
                "thumb2",
                "large2",
                "Deck 2",
                "Description 2"
            ), result[1]
        )
    }

    @Test
    fun `transform should ignore game details without a name or guid`() {
        val searchResponse =
            MockData.searchResponse.copy(
                results = arrayListOf(
                    MockData.validGameDetail1,
                    MockData.invalidGameDetailNoName,
                    MockData.invalidGameDetailNoGuid
                )
            )

        val result = transformer.transform(searchResponse)

        assertEquals(1, result.size)
        assertEquals(
            SearchResultViewData(
                "1",
                "Game 1",
                "thumb1",
                "large1",
                "Deck 1",
                "Description 1"
            ), result[0]
        )
    }

    @Test
    fun `transform should return an empty list if there are no game details with valid names and guids`() {

        val searchResponse =
            MockData.searchResponse.copy(
                results = arrayListOf(
                    MockData.invalidGameDetailNoName,
                    MockData.invalidGameDetailNoGuid
                )
            )

        val result = transformer.transform(searchResponse)

        assertTrue(result.isEmpty())
    }

}