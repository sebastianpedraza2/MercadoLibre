package com.pedraza.sebastian.search_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing

@Composable
fun SearchBar(
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

    //To maintain focus each time the user navigates back from item detail screen
    LaunchedEffect(searchFocused) {
        if (query.isNotEmpty()) focusRequester.requestFocus()
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colors.primary)
            .padding(
                start = spacing.mercadoLibreSpacing16dp,
                end = spacing.mercadoLibreSpacing16dp,
                top = spacing.mercadoLibreSpacing22dp,
                bottom = spacing.mercadoLibreSpacing16dp
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
            color = Color.White,
            contentColor = MaterialTheme.colors.onPrimary,
            shape = MaterialTheme.shapes.small,
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(spacing.mercadoLibreSpacing22dp))
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
            tint = MaterialTheme.colors.secondary,
            contentDescription = null
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.search_section),
            color = MaterialTheme.colors.secondary
        )
    }
}