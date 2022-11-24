package com.pedraza.sebastian.search_data.api

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MercadoLibreService {

    /**
     * Returns all available categories
     * available by [siteId]
     */
    @GET("sites/{site_id}/categories")
    suspend fun getCategories(
        @Path("site_id") siteId: String,
    ): Response<ArrayList<CategoryDto>>

    /**
     * Returns all items that meet the search query
     */
    @GET("sites/{site_id}/search")
    suspend fun searchItems(
        @Path("site_id") siteId: String,
        @Query("limit") pagingLimit: Int,
        @Query("offset") pagingOffset: Int,
        @Query("category") category: String?,
        @Query("q") query: String?
    ): Response<SearchDto>

    /**
     * Returns the detail of an item
     */
    @GET("items")
    suspend fun getItemDetail(
        @Query("ids") itemId: Int
    ): Response<ArrayList<ItemDto>>
}