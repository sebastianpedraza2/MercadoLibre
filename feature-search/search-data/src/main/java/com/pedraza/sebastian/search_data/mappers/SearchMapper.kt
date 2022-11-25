package com.pedraza.sebastian.search_data.mappers

import com.pedraza.sebastian.search_data.entities.dto.search.QueryResultDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.models.search.SearchResultItem

fun SearchDto.toDomain(): SearchResult {
    return with(results) {
        SearchResult(
            itemsPerPage = paging.limit,
            pagingOffset = paging.offset,
            primaryResults = paging.primaryResults,
            results = getResultItems(results) ?: emptyList()
        )
    }
}

fun getResultItems(results: List<QueryResultDto>?): List<SearchResultItem>? {
    return results?.let {
        it.map { item ->
            SearchResultItem(
                itemId = item.itemId,
                title = item.title,
                price = item.price,
                thumbnail = item.thumbnail.orEmpty(),
                availableQuantity = item.availableQuantity,
                soldQuantity = item.soldQuantity
            )
        }
    }
}
