package com.pedraza.sebastian.search_presentation.itemDetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pedraza.sebastian.android_helpers.components.LoadingScreen
import com.pedraza.sebastian.core.utils.ScreenUiState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    upPress: () -> Unit,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value.screenState) {
        ScreenUiState.Init -> viewModel.getItemDetail(itemId)
        ScreenUiState.Loading -> LoadingScreen(color = MaterialTheme.colors.onSurface)
        ScreenUiState.Fetched -> ItemDetailContent(uiState.value.itemDetail, upPress)
        ScreenUiState.Failure -> Unit
    }
}

