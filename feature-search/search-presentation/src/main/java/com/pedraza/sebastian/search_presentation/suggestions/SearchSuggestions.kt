package com.pedraza.sebastian.search_presentation.suggestions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pedraza.sebastian.android_helpers.components.LoadingScreen
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.search_presentation.search.SearchEvent

@Composable
fun SearchSuggestions(
    suggestions: List<String>,
    screenState: ScreenUiState,
    triggerEvent: (SearchEvent) -> Unit,
    ) {

    when (screenState) {
        ScreenUiState.Init -> triggerEvent(SearchEvent.OnGetSuggestions)
        ScreenUiState.Fetched -> SearchSuggestionsContent(suggestions = suggestions) { suggestion ->
            triggerEvent(SearchEvent.OnSuggestionClicked(suggestion))
        }
        ScreenUiState.Loading -> LoadingScreen()
        ScreenUiState.Failure -> Unit
    }
}

@Composable
fun SearchSuggestionsContent(
    suggestions: List<String>,
    onSuggestionSelect: (String) -> Unit
) {
    val spacing = LocalSpacing.current

    LazyColumn {
        item {
            SuggestionHeader(stringResource(id = R.string.suggestions_header))
        }
        items(suggestions) { suggestion ->
            Suggestion(
                suggestion = suggestion,
                onSuggestionSelect = onSuggestionSelect,
                modifier = Modifier.fillParentMaxWidth()
            )
        }
        item {
            Spacer(Modifier.height(spacing.mercadoLibreSpacing8dp))
        }
    }
}

@Composable
private fun SuggestionHeader(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colors.onSurface,
        modifier = modifier
            .heightIn(min = 56.dp)
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .wrapContentHeight()
    )
}

@Composable
private fun Suggestion(
    suggestion: String,
    onSuggestionSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = suggestion,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
        modifier = modifier
            .heightIn(min = 48.dp)
            .clickable { onSuggestionSelect(suggestion) }
            .padding(start = 24.dp)
            .wrapContentSize(Alignment.CenterStart)
    )
}
