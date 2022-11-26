package com.pedraza.sebastian.search_data.api

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDescriptionDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreService {

    @GET("sites/{site_id}/categories")
    suspend fun getCategories(
        @Path("site_id") siteId: String,
    ): Response<ArrayList<CategoryDto>>

    @GET("sites/{site_id}/search")
    suspend fun searchItems(
        @Path("site_id") siteId: String,
        @Query("limit") pagingLimit: Int,
        @Query("offset") pagingOffset: Int,
        @Query("category") category: String?,
        @Query("q") query: String?
    ): Response<SearchDto>

    @GET("items/{item_id}")
    suspend fun getItemDetail(
        @Path("item_id") itemId: String,
        @Query("include_attributes") includeAttributes: String
    ): Response<ItemDto>

    @GET("items/{item_id}/description")
    suspend fun getItemDescription(
        @Path("item_id") itemId: String
    ): Response<ItemDescriptionDto>
}