package com.pedraza.sebastian.search_data.repository

import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.core.utils.*
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSource
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.search_domain.models.item.Item
import com.pedraza.sebastian.search_data.mappers.toDomain
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.repository.SearchRepository


class SearchRepositoryImpl(private val dataSource: SearchRemoteDataSource) : SearchRepository {

    override suspend fun getCategories(siteId: String): Result<List<Category>> =
        resolveResponse(dataRequest = { dataSource.getCategories(siteId) }) { categoryList ->
            val domainCategoryList = categoryList.map {
                it.toDomain()
            }
            Result.Success(domainCategoryList)
        }

    override suspend fun searchItems(
        siteId: String,
        itemsPerPage: Int,
        pagingOffset: Int,
        category: String?,
        query: String?
    ): Result<SearchResult> =
        resolveResponse(dataRequest = {
            dataSource.searchItems(
                siteId = siteId,
                itemsPerPage = itemsPerPage,
                pagingOffset = pagingOffset,
                category = category,
                query = query
            )
        }) { searchDto ->
            Result.Success(searchDto.toDomain())
        }


    override suspend fun getItemDetail(itemId: String): Result<Item> {
        return try {
            val itemDetail = dataSource.getItemDetail(itemId)
            val itemDescription = dataSource.getItemDescription(itemId)

            if (isResponseValid(itemDetail) && isResponseValid(itemDescription)) {
                Result.Success(itemDetail.body()!!.toDomain(itemDescription.body()!!))
            } else {
                Result.Error(UiText.ResourcesString(R.string.meli_default_error))
            }
        } catch (error: Exception) {
            Result.Error(UiText.DynamicString(error.message.toString()))
        }
    }
}
