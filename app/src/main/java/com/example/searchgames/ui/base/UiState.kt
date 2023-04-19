package com.example.searchgames.ui.base

sealed interface UiState<out T> {

    object Success: UiState<Nothing>

    data class Error(val message: String) : UiState<Nothing>

    object Loading : UiState<Nothing>

    object LoadingMore : UiState<Nothing>

    object Empty: UiState<Nothing>

}