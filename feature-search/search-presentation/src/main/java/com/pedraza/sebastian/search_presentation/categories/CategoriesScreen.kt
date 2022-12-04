package com.pedraza.sebastian.search_presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.pedraza.sebastian.android_helpers.components.LoadingScreen
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.core.R
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
            ScreenUiState.Loading -> LoadingScreen(color = MaterialTheme.colors.secondary)
            ScreenUiState.Fetched -> SearchCategoriesContent(categories = categories)
            ScreenUiState.Init -> triggerEvent(SearchEvent.OnGetCategories)
            else -> Unit
        }

    }


}

@Composable
fun SearchCategoriesContent(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    val spacing = LocalSpacing.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.categories_section),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(
                        top = spacing.mercadoLibreSpacing22dp,
                        bottom = spacing.mercadoLibreSpacing16dp,
                    )
                    .wrapContentHeight()
            )
        }
        itemsIndexed(categories) { index, item ->
            CategorySearchItem(item = item, index = index, size = categories.size)
        }
        item {
            Spacer(Modifier.height(spacing.mercadoLibreSpacing32dp))
        }

    }
}

@Composable
fun CategorySearchItem(item: Category, index: Int, size: Int, modifier: Modifier = Modifier) {
    val spacing = LocalSpacing.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                // Todo make categories clickable, consume SearchByCategoryUseCase
            },
        shape = when (index) {
            0 -> RoundedCornerShape(
                topStart = spacing.mercadoLibreSpacing8dp,
                topEnd = spacing.mercadoLibreSpacing8dp
            )
            size - 1 -> RoundedCornerShape(
                bottomStart = spacing.mercadoLibreSpacing8dp,
                bottomEnd = spacing.mercadoLibreSpacing8dp
            )
            else -> RoundedCornerShape(spacing.mercadoLibreSpacing0dp)
        },
        elevation = spacing.mercadoLibreSpacing4dp
    ) {
        Row(
            modifier = modifier
                .padding(
                    spacing.mercadoLibreSpacing8dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.name,
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    spacing.mercadoLibreSpacing8dp
                )
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_forward_ios_24),
                modifier = Modifier.padding(
                    end = spacing.mercadoLibreSpacing8dp,
                ), tint = MaterialTheme.colors.onPrimary,
                contentDescription = null
            )
        }


    }
}

