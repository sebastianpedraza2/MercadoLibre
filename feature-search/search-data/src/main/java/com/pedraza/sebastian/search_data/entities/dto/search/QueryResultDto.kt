package com.pedraza.sebastian.search_data.entities.dto.search


import com.google.gson.annotations.SerializedName

data class QueryResultDto(
    @SerializedName("available_quantity")
    val availableQuantity: Int?,
    @SerializedName("id")
    val itemId: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("sold_quantity")
    val soldQuantity: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("installments")
    val installments: Installments?
)