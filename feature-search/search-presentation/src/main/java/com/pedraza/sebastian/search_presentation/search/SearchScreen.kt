package com.pedraza.sebastian.search_presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.pedraza.sebastian.core.R
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.search_presentation.categories.SearchCategories
import com.pedraza.sebastian.search_presentation.components.SearchBar
import com.pedraza.sebastian.search_presentation.components.SearchNoResults

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
                        screenState = uiState.value.screenUiState,
                        triggerEvent = viewModel::onEvent
                    )
                }
                SearchDisplay.Suggestions -> SearchSuggestions(
                    suggestions = uiState.value.suggestions,
                    onSuggestionSelect = { }
                )
                SearchDisplay.Results -> {
                    SearchResultList(
                        items = uiState.value.items,
                        isSearching = uiState.value.isSearching,
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
