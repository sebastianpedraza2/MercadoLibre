package com.pedraza.sebastian.search_domain.models.search

data class SearchResult(
    val itemsPerPage: Int? = 0,
    val pagingOffset: Int? = 0,
    val primaryResults: Int? = 0,
    val results: List<SearchResultItem>
)
