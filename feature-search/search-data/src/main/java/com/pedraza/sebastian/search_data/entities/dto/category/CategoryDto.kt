package com.pedraza.sebastian.search_data.entities.dto.category


import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String?
)