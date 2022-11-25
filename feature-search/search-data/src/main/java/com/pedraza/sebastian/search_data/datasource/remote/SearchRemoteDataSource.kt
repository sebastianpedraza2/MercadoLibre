package com.pedraza.sebastian.search_data.datasource.remote

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDescriptionDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import retrofit2.Response

interface SearchRemoteDataSource {

    /**
     * Returns all available categories by [siteId]
     */
    suspend fun getCategories(siteId: String): Response<ArrayList<CategoryDto>>

    /**
     * Returns all items that meet the search query: [query] and [category]
     */
    suspend fun searchItems(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?,
        query: String?
    ): Response<SearchDto>

    /**
     * Returns the detail of the item identified with [itemId]
     */
    suspend fun getItemDetail(itemId: String): Response<ItemDto>

    /**
     * Returns the description of the item identified with [itemId]
     */
    suspend fun getItemDescription(itemId: String): Response<ItemDescriptionDto>
}