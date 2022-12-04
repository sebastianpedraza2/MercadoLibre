package com.pedraza.sebastian.search_presentation.search

import com.pedraza.sebastian.core.pagination.PaginatorApi
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.search.SearchResult

class SearchPaginator(
    initialOffset: Int,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextOffset: Int) -> Result<SearchResult>,
    private val getNextOffset: suspend (SearchResult) -> Int,
    private val onError: suspend (UiText) -> Unit,
    private val onSuccess: suspend (result: SearchResult, newOffset: Int) -> Unit
) : PaginatorApi {

    private var currentOffset = initialOffset
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest && currentOffset >= 1) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentOffset)
        isMakingRequest = false
        if (result is Result.Success) {
            currentOffset = getNextOffset(result.data)
            onSuccess(result.data, currentOffset)
            onLoadUpdated(false)
        } else if (result is Result.Error) {
            onError(result.message)
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentOffset = 0
    }
}

