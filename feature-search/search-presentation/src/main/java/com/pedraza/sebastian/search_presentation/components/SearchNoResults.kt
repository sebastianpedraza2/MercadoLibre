package com.pedraza.sebastian.search_presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.dimensions.LocalSpacing

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