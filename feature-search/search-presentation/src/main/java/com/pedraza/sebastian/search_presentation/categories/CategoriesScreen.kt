package com.pedraza.sebastian.search_presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pedraza.sebastian.android_helpers.components.LoadingScreen
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.search_presentation.search.SearchEvent

@Composable
fun SearchCategories(
    categories: List<Category>,
    screenState: ScreenUiState,
    modifier: Modifier = Modifier,
    triggerEvent: (SearchEvent) -> Unit
) {
    val spacing = LocalSpacing.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = spacing.mercadoLibreSpacing16dp),

        contentAlignment = Alignment.Center
    ) {
        when (screenState) {
            ScreenUiState.Loading -> LoadingScreen()
            ScreenUiState.Fetched -> CategoriesContent(
                categories = categories,
                triggerEvent = triggerEvent
            )
            ScreenUiState.Init -> triggerEvent(SearchEvent.OnGetCategories)
            else -> Unit
        }
    }
}

