package com.pedraza.sebastian.search_domain.models.search

data class SearchResultItem(
    val itemId: String,
    val title: String? = "",
    val price: Int? = 0,
    val thumbnail: String?,
    val availableQuantity: Int?,
    val soldQuantity: Int?
)