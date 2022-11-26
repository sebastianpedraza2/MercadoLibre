package com.pedraza.sebastian.search_domain.usecases.items

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.repository.SearchRepository

class GetItemsByCategoryUseCaseImpl(private val repository: SearchRepository) :
    GetItemsByCategoryUseCase {
    override suspend fun invoke(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?
    ): Result<SearchResult> = repository.searchItems(
        siteId = siteId,
        itemsPerPage = itemsPerPage,
        pagingOffset = pagingOffset,
        query = null,
        category = category
    )
}