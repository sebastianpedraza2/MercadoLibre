package com.pedraza.sebastian.search_data.datasource.remote

import com.pedraza.sebastian.core.utils.Constants.INCLUDE_ATTRIBUTES_PARAM
import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDescriptionDto
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

    override suspend fun getItemDetail(itemId: String): Response<ItemDto> =
        mercadoLibreService.getItemDetail(
            includeAttributes = INCLUDE_ATTRIBUTES_PARAM,
            itemId = itemId
        )

    override suspend fun getItemDescription(itemId: String): Response<ItemDescriptionDto> =
        mercadoLibreService.getItemDescription(itemId)
}