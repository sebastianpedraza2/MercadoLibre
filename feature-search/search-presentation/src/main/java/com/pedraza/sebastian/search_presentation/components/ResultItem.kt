package com.pedraza.sebastian.search_presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pedraza.sebastian.android_helpers.components.MeliDivider
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing
import com.pedraza.sebastian.core.utils.formatPrice
import com.pedraza.sebastian.core.utils.formatPriceWithCurrency
import com.pedraza.sebastian.search_domain.models.search.SearchResultItem

@Composable
fun ResultItem(
    item: SearchResultItem,
    showDivider: Boolean,
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit,
) {
    val spacing = LocalSpacing.current
    Column(modifier = modifier
        .fillMaxWidth()
        .clickable {
            onItemClick(item.itemId)
        }) {
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
        ResultItem(
            item = itemsMock[0],
            showDivider = false
        ) {}
    }
}