package com.pedraza.sebastian.search_data.entities.dto.search


import com.google.gson.annotations.SerializedName

data class SearchDto(
    @SerializedName("paging")
    val paging: PagingDto,
    @SerializedName("results")
    val results: List<QueryResultDto>?,
    @SerializedName("site_id")
    val siteId: String?,
)