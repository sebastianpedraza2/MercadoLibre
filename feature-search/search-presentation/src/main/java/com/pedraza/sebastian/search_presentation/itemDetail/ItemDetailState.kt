package com.pedraza.sebastian.search_presentation.itemDetail

import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.search_domain.models.item.Item

data class ItemDetailState(
    val itemDetail: Item = Item(),
    val screenState: ScreenUiState = ScreenUiState.Init
)