package com.example.searchgames.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.searchgames.data.repository.SearchRepository
import com.example.searchgames.ui.search.SearchResultTransformer
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel
import com.example.searchgames.util.DispatcherProvider
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")

class ViewModelFactory @Inject constructor(
    private val searchRepository: SearchRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val searchResultTransformer: SearchResultTransformer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SearchGamesViewModel::class.java)) {
            return SearchGamesViewModel(searchRepository, dispatcherProvider, searchResultTransformer) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}