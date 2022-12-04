package com.pedraza.sebastian.search_domain.models.search

data class SearchResultItem(
    val itemId: String,
    val title: String = "",
    val price: Double,
    val thumbnail: String,
    val availableQuantity: Int,
    val soldQuantity: Int,
    val installmentsQuantity: Int?,
    val installmentsAmount: Double?
)