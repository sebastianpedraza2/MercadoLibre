package com.pedraza.sebastian.search_data.repository

import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.search_domain.models.item.Item
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.search.SearchResult

interface SearchRepository {

    /**
     * Returns all available categories by [siteId] in a [Result] wrapper
     * @param siteId: Used to filter the categories by site
     * @return [Result] wrapper
     */
    suspend fun getCategories(siteId: String): Result<List<Category>>

    /**
     * Returns all items that meet the search query: [query] and [category] in a [Result] wrapper
     * @param siteId: Used to filter the query by site
     * @param itemsPerPage: Used for pagination
     * @param pagingOffset: Used for pagination
     * @param category: Used to filter the query by category
     * @param query: query string
     * @return [Result] wrapper
     */
    suspend fun searchItems(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?,
        query: String?
    ): Result<SearchResult>

    /**
     * Returns the detail of the item identified with [itemId] including it's description
     * @param itemId: The id of the item
     * @return [Result] wrapper
     */
    suspend fun getItemDetail(itemId: String): Result<Item>
}