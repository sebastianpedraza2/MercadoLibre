package com.pedraza.sebastian.search_presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.search_presentation.categories.SearchCategories
import com.pedraza.sebastian.search_presentation.components.SearchBar
import com.pedraza.sebastian.search_presentation.components.SearchNoResults
import com.pedraza.sebastian.search_presentation.suggestions.SearchSuggestions

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchScreen(
    onItemClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val query = viewModel.query.collectAsStateWithLifecycle()
    val searchDisplay = viewModel.searchDisplay.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.statusBarsPadding())
            SearchBar(
                query = query.value,
                onQueryChange = { viewModel.onEvent(SearchEvent.OnSearchItems(it)) },
                searchFocused = uiState.value.isFocused,
                onSearchFocusChange = { viewModel.onEvent(SearchEvent.OnFocusChanged(it)) },
                onClearQuery = { viewModel.onEvent(SearchEvent.OnClearQuery) },
            )
            MeliDivider()
            when (searchDisplay.value) {
                SearchDisplay.Categories -> {
                    SearchCategories(
                        categories = uiState.value.categories,
                        screenState = uiState.value.categoriesScreenState,
                        triggerEvent = viewModel::onEvent
                    )
                }
                SearchDisplay.Suggestions ->
                    SearchSuggestions(
                    suggestions = uiState.value.suggestions,
                    screenState = uiState.value.suggestionsScreenState,
                    triggerEvent = viewModel::onEvent
                )
                SearchDisplay.Results -> {
                    SearchResults(
                        items = uiState.value.items,
                        isSearching = uiState.value.isSearching,
                        screenState = uiState.value.resultsScreenState,
                        primaryResults = uiState.value.primaryResults,
                        onItemClick = onItemClick,
                        triggerEvent = viewModel::onEvent
                    )
                }
                SearchDisplay.NoResults -> {
                    SearchNoResults(query.value)
                }
            }
        }
    }
}
