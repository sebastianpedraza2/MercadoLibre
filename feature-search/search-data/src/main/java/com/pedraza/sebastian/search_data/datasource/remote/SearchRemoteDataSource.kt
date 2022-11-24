package com.pedraza.sebastian.search_data.datasource.remote

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import retrofit2.Response

interface SearchRemoteDataSource {
    suspend fun getCategories(siteId: String): Response<ArrayList<CategoryDto>>
    suspend fun searchItems(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?,
        query: String?
    ): Response<SearchDto>
    suspend fun getItemDetail(itemId: Int): Response<ArrayList<ItemDto>>
}