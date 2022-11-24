package com.pedraza.sebastian.search_data.entities.dto.search


import com.google.gson.annotations.SerializedName

data class QueryResultDto(
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
)