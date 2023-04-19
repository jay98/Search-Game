package com.example.searchgames.ui.search

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.searchgames.databinding.FragmentSearchBinding
import com.example.searchgames.ui.base.UiState
import com.example.searchgames.ui.viewmodel.SearchGamesViewModel

object SearchFragmentHelper {

    suspend fun updateSearchListState(
        viewModel: SearchGamesViewModel,
        binding: FragmentSearchBinding,
        context: Context?,
        adapter: SearchResultAdapter
    ) {
        viewModel.apiCallState.collect {
            when (it) {
                is UiState.Empty -> {
                    binding.searchProgressBar.visibility = View.GONE
                    binding.loadMoreProgressBar.visibility = View.GONE
                    binding.initialSearchView.visibility = View.VISIBLE
                }
                is UiState.Success -> {
                    binding.searchProgressBar.visibility = View.GONE
                    binding.loadMoreProgressBar.visibility = View.GONE
                    binding.initialSearchView.visibility = View.GONE
                    adapter.submitList(viewModel.searchResult.value)
                }
                is UiState.Loading -> {
                    binding.searchProgressBar.visibility = View.VISIBLE
                    binding.initialSearchView.visibility = View.GONE
                }
                is UiState.LoadingMore -> {
                    binding.loadMoreProgressBar.visibility = View.VISIBLE
                    binding.initialSearchView.visibility = View.GONE
                }
                is UiState.Error -> {
                    //Handle Error
                    binding.searchProgressBar.visibility = View.GONE
                    binding.loadMoreProgressBar.visibility = View.GONE
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}