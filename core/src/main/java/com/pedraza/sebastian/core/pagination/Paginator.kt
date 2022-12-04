package com.pedraza.sebastian.core.pagination

/**
 * Api that provides common pagination capabilities
 */
interface PaginatorApi {
    suspend fun loadNextItems()
    fun reset()
}