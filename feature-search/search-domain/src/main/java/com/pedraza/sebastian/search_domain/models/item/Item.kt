package com.pedraza.sebastian.search_domain.models.item

data class Item(
    val id: String,
    val title: String? = "",
    val description: String,
    val itemCondition: String? = "",
    val availableQuantity: Int? = 0,
    val initialQuantity: Int? = 0,
    val pictures: List<String?>? = emptyList(),
    val price: Int? = 0,
    val soldQuantity: Int? = 0,
    val thumbnail: String? = "",
    val warranty: String? = "",
    val categoryId: String? = ""
)
