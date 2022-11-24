package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName

data class AttributeDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("value_name")
    val valueName: String,
)