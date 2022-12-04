package com.pedraza.sebastian.core.utils

/**
 * Holder that represents different states of a generic screen
 */
sealed interface ScreenUiState {
    object Init : ScreenUiState
    object Loading : ScreenUiState
    object Fetched : ScreenUiState
    object Failure : ScreenUiState
}