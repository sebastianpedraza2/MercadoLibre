package com.pedraza.sebastian.search_data.entities.dto.search


import com.google.gson.annotations.SerializedName

data class PagingDto(
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("primary_results")
    val primaryResults: Int,
    @SerializedName("total")
    val total: Int
)