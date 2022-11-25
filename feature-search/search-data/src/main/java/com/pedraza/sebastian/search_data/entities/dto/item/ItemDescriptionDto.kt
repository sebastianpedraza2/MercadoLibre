package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName

data class ItemDescriptionDto(
    @SerializedName("plain_text")
    val plainText: String?,
)