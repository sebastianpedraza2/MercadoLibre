package com.pedraza.sebastian.search_presentation.utils

import com.pedraza.sebastian.core.pagination.PaginatorApi
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_presentation.search.SearchPaginator

/**
 * Factory class to provide [PaginatorApi] implementations
 */
class PaginationFactory {
    /**
     * Provides a [PaginatorApi] implementation for SearchResult
     * @return a new [SearchPaginator] instance
     */
    fun getSearchPaginator(
        initialOffset: Int,
        onLoadUpdated: (Boolean) -> Unit,
        onRequest: suspend (nextOffset: Int) -> Result<SearchResult>,
        getNextOffset: suspend (SearchResult) -> Int,
        onError: suspend (UiText) -> Unit,
        onSuccess: suspend (result: SearchResult, newOffset: Int) -> Unit
    ): PaginatorApi =
        SearchPaginator(initialOffset, onLoadUpdated, onRequest, getNextOffset, onError, onSuccess)
}