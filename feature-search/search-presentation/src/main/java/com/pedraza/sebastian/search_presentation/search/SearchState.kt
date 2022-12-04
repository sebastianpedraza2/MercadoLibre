package com.pedraza.sebastian.search_presentation.search

import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.search_domain.models.search.SearchResultItem

data class SearchState(
    val items: List<SearchResultItem> = emptyList(),
    val categories: List<Category> = emptyList(),
    val primaryResults: Int = 0,
    val isSearching: Boolean = false,
    val endReached: Boolean = false,
    val offset: Int = 0,
    val isFocused: Boolean = true,
    val suggestions: List<SearchSuggestionGroup> = searchSuggestions,
    val screenUiState: ScreenUiState = ScreenUiState.Init
)

enum class SearchDisplay {
    Categories, Suggestions, Results, NoResults
}