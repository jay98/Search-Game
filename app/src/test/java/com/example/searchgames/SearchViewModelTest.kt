package com.example.searchgames

import com.example.searchgames.data.repository.SearchRepository
import com.example.searchgames.ui.base.UiState
import com.example.searchgames.ui.search.SearchResultTransformer
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel
import com.example.searchgames.util.DispatcherProvider
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchGamesViewModelTest {

    lateinit var searchRepository: SearchRepository

    lateinit var dispatcherProvider: DispatcherProvider

    lateinit var searchResultTransformer: SearchResultTransformer

    private lateinit var viewModel: SearchGamesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchRepository = mockk()
        dispatcherProvider = mockk()
        searchResultTransformer = mockk()
    }

    @Test
    fun `searchGame with empty search term should not trigger searchRepository#searchGames when not the initial load`() {

        viewModel = SearchGamesViewModel(searchRepository, dispatcherProvider, searchResultTransformer)
        // given
        coEvery { searchRepository.searchGames(any()) } returns flow { }
        coEvery { dispatcherProvider.main } returns Dispatchers.Unconfined

        // when
        viewModel.searchGame()
        viewModel.searchGame()

        // then
        coVerify(exactly = 1) { searchRepository.searchGames(any()) }
    }

    @Test
    fun `searchGame with new search term should trigger searchRepository#searchGames`() = runBlocking {
        // given
        viewModel = SearchGamesViewModel(searchRepository, dispatcherProvider, searchResultTransformer)
        val searchTerm = "new search term"
        coEvery { searchRepository.searchGames(searchTerm) } returns flowOf(MockData.searchResponse)
        coEvery { searchResultTransformer.transform(MockData.searchResponse) } returns listOf(MockData.searchResultViewData)
        every { dispatcherProvider.main } returns Dispatchers.Unconfined


        // when
        viewModel.searchGame(searchTerm)

        // then
        coVerify(exactly = 1) { searchRepository.searchGames(searchTerm) }
        assertEquals(viewModel.searchResult.value, listOf(MockData.searchResultViewData))
        assertEquals(viewModel.apiCallState.value, UiState.Success)
    }

    @Test
    fun `loadMoreGames should trigger searchRepository#loadMoreGames`() = runBlocking {
        // given
        viewModel = SearchGamesViewModel(searchRepository, dispatcherProvider, searchResultTransformer)
        val searchTerm = "load more games"
        every { dispatcherProvider.main } returns Dispatchers.Unconfined
        coEvery { searchRepository.loadMoreGames(searchTerm, any()) } returns flowOf(MockData.searchResponse)
        coEvery { searchResultTransformer.transform(MockData.searchResponse) } returns listOf(MockData.searchResultViewData)
        coEvery { searchRepository.searchGames(searchTerm) } returns flowOf(MockData.searchResponse)


        viewModel.searchGame(searchTerm)
        // when
        viewModel.loadMoreGames()

        // then
        coVerify(exactly = 1) { searchRepository.loadMoreGames(searchTerm, any()) }
        assertEquals(viewModel.searchResult.value, listOf(MockData.searchResultViewData, MockData.searchResultViewData))
        assertEquals(viewModel.apiCallState.value, UiState.Success)
    }
}
