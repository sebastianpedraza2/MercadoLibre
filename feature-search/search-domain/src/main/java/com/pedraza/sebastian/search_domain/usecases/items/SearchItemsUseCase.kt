package com.pedraza.sebastian.search_domain.usecases.items

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.search.SearchResult

interface SearchItemsUseCase {
    suspend fun invoke(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        query: String?
    ): Result<SearchResult>
}