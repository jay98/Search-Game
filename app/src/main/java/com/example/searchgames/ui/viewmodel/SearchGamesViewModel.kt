package com.example.searchgames.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchgames.data.repository.SearchRepository
import com.example.searchgames.ui.base.UiState
import com.example.searchgames.ui.search.SearchResultTransformer
import com.example.searchgames.ui.search.SearchResultViewData
import com.example.searchgames.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchGamesViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val searchResultTransformer: SearchResultTransformer
) : ViewModel() {
    private val _apiCallState = MutableStateFlow<UiState<Nothing>>(UiState.Empty)
    val apiCallState: StateFlow<UiState<Nothing>> = _apiCallState

    private val _searchResult = MutableStateFlow<List<SearchResultViewData>>(emptyList())
    val searchResult: StateFlow<List<SearchResultViewData>> = _searchResult

    private var initialLoad = true
    private var totalResults: Int = 0
    private var currentOffset: Int = 0
    private var currentSearchTerm: String = ""


    fun searchGame(searchTerm: String = "") {
        viewModelScope.launch(dispatcherProvider.main) {
            if (!shouldSearch(searchTerm)) return@launch

            currentSearchTerm = searchTerm

            resetLoadMore()
            _apiCallState.value = UiState.Loading
            searchRepository
                .searchGames(searchTerm)
                .catch { e ->
                    _apiCallState.value = UiState.Error(e.toString())
                }
                .collect { response ->
                    totalResults = response.numberOfTotalResults
                    currentOffset += response.numberOfPageResults
                    _searchResult.value = searchResultTransformer.transform(response)
                    if (response.numberOfPageResults == 0) _apiCallState.value = UiState.Empty
                    else _apiCallState.value = UiState.Success
                }
        }
    }

    private fun shouldSearch(searchTerm: String): Boolean =
        when {
            initialLoad -> {
                initialLoad = false
                true
            }
            _apiCallState.value is UiState.Loading -> false
            currentSearchTerm == searchTerm -> false
            else -> true
        }


    fun loadMoreGames() {
        viewModelScope.launch(dispatcherProvider.main) {
            if (!shouldLoadMore()) return@launch

            _apiCallState.value = UiState.LoadingMore
            searchRepository
                .loadMoreGames(currentSearchTerm, currentOffset)
                .catch { e ->
                    _apiCallState.value = UiState.Error(e.toString())
                }
                .collect { response ->
                    currentOffset += response.numberOfPageResults
                    _searchResult.value =
                        _searchResult.value + searchResultTransformer.transform(response)
                    _apiCallState.value = UiState.Success
                }
        }
    }

    private fun shouldLoadMore(): Boolean =
        when {
            _apiCallState.value is UiState.Loading -> false
            _apiCallState.value is UiState.LoadingMore -> false
            currentOffset >= totalResults -> false
            else -> true
        }

    private fun resetLoadMore() {
        _searchResult.value = emptyList()
        totalResults = 0
        currentOffset = 0
    }
}