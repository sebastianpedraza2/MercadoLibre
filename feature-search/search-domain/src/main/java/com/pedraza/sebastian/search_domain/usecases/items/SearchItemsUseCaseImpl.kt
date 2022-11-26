package com.pedraza.sebastian.search_domain.usecases.items

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.repository.SearchRepository

class SearchItemsUseCaseImpl(private val repository: SearchRepository) : SearchItemsUseCase {
    override suspend fun invoke(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        query: String?
    ): Result<SearchResult> = repository.searchItems(
        siteId = siteId,
        itemsPerPage = itemsPerPage,
        pagingOffset = pagingOffset,
        query = query,
        category = null
    )
}