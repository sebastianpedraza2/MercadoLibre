package com.pedraza.sebastian.search_presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.search_domain.models.search.SearchResultItem
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.core.utils.formatPrice
import com.pedraza.sebastian.core.utils.formatPriceWithCurrency
import com.pedraza.sebastian.search_presentation.components.ItemImage


@Composable
fun SearchResultList(
    items: List<SearchResultItem>,
    primaryResults: Int,
    isSearching: Boolean,
    modifier: Modifier = Modifier,
    triggerEvent: (SearchEvent) -> Unit,
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
                SearchResultItem(item = item, triggerEvent = triggerEvent, showDivider = index != 0)
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
private fun SearchResultItem(
    item: SearchResultItem,
    showDivider: Boolean,
    modifier: Modifier = Modifier,
    triggerEvent: (SearchEvent) -> Unit
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable { triggerEvent(SearchEvent.OnItemClicked(item.itemId)) }) {
        if (showDivider) {
            MeliDivider()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(spacing.mercadoLibreSpacing16dp)
        ) {
            ItemImage(
                imageUrl = item.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(130.dp)
            )
            Column(modifier = Modifier.padding(start = spacing.mercadoLibreSpacing16dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    Modifier.height(spacing.mercadoLibreSpacing8dp)
                )
                Text(
                    text = formatPriceWithCurrency(item.price),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(
                    Modifier.height(spacing.mercadoLibreSpacing8dp)
                )
                if (item.installmentsQuantity != null && item.installmentsAmount != null) {
                    Text(
                        text = stringResource(
                            id = R.string.installments,
                            item.installmentsQuantity!!,
                            formatPrice(item.installmentsAmount!!)
                        ),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }
    }

}

@Composable
fun SearchNoResults(
    query: String,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize()
            .padding(spacing.mercadoLibreSpacing22dp)
    ) {
        Image(
            painterResource(R.drawable.noresults),
            contentDescription = null
        )
        Spacer(Modifier.height(spacing.mercadoLibreSpacing16dp))
        Text(
            text = stringResource(R.string.search_no_matches, query),
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(spacing.mercadoLibreSpacing16dp))
        Text(
            text = stringResource(R.string.search_no_matches_retry),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
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

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
private fun SearchResultPreview() {
    val itemsMock = listOf(
        SearchResultItem(
            "1",
            title = "Iphone 14 Iphone 14 Iphone 14 Iphone 14 Iphone 14",
            price = 12312.0123123,
            thumbnail = "http://http2.mlstatic.com/D_866268-CBT48286360406_112021-I.jpg",
            availableQuantity = 4,
            soldQuantity = 2,
            installmentsAmount = 3500.023213,
            installmentsQuantity = 24
        )
    )
    Surface {
        SearchResultItem(
            item = itemsMock[0],
            triggerEvent = { },
            showDivider = false
        )
    }

}