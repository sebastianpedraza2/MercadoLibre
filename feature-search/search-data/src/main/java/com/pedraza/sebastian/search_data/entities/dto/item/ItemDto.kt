package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName

data class ItemDto(
    @SerializedName("body")
    val body: ItemBodyDto,
    @SerializedName("code")
    val code: Int
)

