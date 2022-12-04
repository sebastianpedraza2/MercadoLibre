package com.pedraza.sebastian.search_presentation.search

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.search_presentation.categories.SearchCategories

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchScreen(
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
                        triggerEvent = viewModel::onEvent,
                        isSearching = uiState.value.isSearching,
                        primaryResults = uiState.value.primaryResults
                    )
                }
                SearchDisplay.NoResults -> {
                    SearchNoResults(query.value)
                }
            }
        }
    }
}


@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    searchFocused: Boolean = false,
    onSearchFocusChange: (Boolean) -> Unit,
    onClearQuery: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val spacing = LocalSpacing.current

    Row(  verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(
                horizontal = spacing.mercadoLibreSpacing16dp,
                vertical = spacing.mercadoLibreSpacing8dp
            )
    ) {
        if (searchFocused) {
            Text(
                text = stringResource(R.string.cancel_search),
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(end = spacing.mercadoLibreSpacing8dp)
                    .clickable {
                        focusManager.clearFocus()
                        onClearQuery.invoke()
                    }
            )
        }
        Surface(
            color = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onPrimary,
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(spacing.mercadoLibreSpacing8dp))
        ) {
            Box(Modifier.fillMaxSize()) {
                if (query.isEmpty()) {
                    SearchHint()
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                ) {
                    BasicTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = spacing.mercadoLibreSpacing8dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                onSearchFocusChange(it.isFocused)
                            }
                    )
                }
            }
        }

    }

}

@Composable
private fun SearchHint() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_section),
            color = MaterialTheme.colors.secondary
        )
    }
}