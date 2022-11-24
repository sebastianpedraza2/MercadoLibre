package com.pedraza.sebastian.search_data.datasource.remote

import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import retrofit2.Response

class SearchRemoteDataSourceImpl(
    private val mercadoLibreService: MercadoLibreService
) : SearchRemoteDataSource {

    override suspend fun getCategories(siteId: String): Response<ArrayList<CategoryDto>> =
        mercadoLibreService.getCategories(siteId)

    override suspend fun searchItems(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?,
        query: String?
    ): Response<SearchDto> =
        mercadoLibreService.searchItems(siteId, itemsPerPage, pagingOffset, category, query)

    override suspend fun getItemDetail(itemId: Int): Response<ArrayList<ItemDto>> =
        mercadoLibreService.getItemDetail(itemId)
}