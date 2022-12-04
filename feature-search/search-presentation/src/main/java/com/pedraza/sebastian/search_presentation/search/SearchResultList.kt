package com.pedraza.sebastian.search_presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.search_domain.models.search.SearchResultItem
import com.pedraza.sebastian.search_presentation.components.ResultItem


@Composable
fun SearchResultList(
    items: List<SearchResultItem>,
    primaryResults: Int,
    isSearching: Boolean,
    modifier: Modifier = Modifier,
    triggerEvent: (SearchEvent) -> Unit,
    onItemClick: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    val listState = rememberLazyListState()
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.search_count, primaryResults),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )
        MeliDivider()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState
        ) {
            itemsIndexed(items) { index, item ->
                ResultItem(item = item, showDivider = index != 0, onItemClick = onItemClick)
            }
            item {
                if (isSearching && items.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.onSurface)
                    }
                }
            }
            item {
                Spacer(Modifier.height(spacing.mercadoLibreSpacing32dp))
            }
        }
        listState.OnBottomReached(buffer = 5) {
            triggerEvent(SearchEvent.OnLoadNewItems)
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore: () -> Unit
) {
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}
