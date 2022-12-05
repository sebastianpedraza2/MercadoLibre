package com.pedraza.sebastian.search_data.datasource.remote

import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDescriptionDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchRemoteDataSourceImplTest {
    private lateinit var searchRemoteDataSourceImpl: SearchRemoteDataSourceImpl
    private val mockMercadoLibreService: MercadoLibreService = mockk(relaxed = true)
    private val mockItemDescriptionDto: ItemDescriptionDto = mockk(relaxed = true)
    private val mockCategoryDto: ArrayList<CategoryDto> = mockk(relaxed = true)
    private val mockSearchDto: SearchDto = mockk(relaxed = true)
    private val mockItemDto: ItemDto = mockk(relaxed = true)
    private val siteId = "MCO"
    private val itemId = "MCO1073403941"


    @Before
    fun setUp() {
        searchRemoteDataSourceImpl = SearchRemoteDataSourceImpl(mockMercadoLibreService)
    }

    @Test
    fun `test should call GetCategories service`() = runBlocking {
        //given
        val responseExpected = Response.success(mockCategoryDto)
        coEvery { mockMercadoLibreService.getCategories(siteId) } returns responseExpected
        //when
        val response = searchRemoteDataSourceImpl.getCategories(siteId)
        //then
        coVerify(exactly = 1) { mockMercadoLibreService.getCategories(siteId) }
        assertEquals(responseExpected, response)
    }

    @Test
    fun `test should call SearchItem service`() = runBlocking {
        //given
        val itemsPerPage = 10
        val pagingOffset = 0
        val category = ""
        val query = ""
        val responseExpected = Response.success(mockSearchDto)
        coEvery {
            mockMercadoLibreService.searchItems(
                siteId,
                itemsPerPage,
                pagingOffset,
                category,
                query
            )
        } returns responseExpected
        //when
        val response = searchRemoteDataSourceImpl.searchItems(
            siteId,
            itemsPerPage,
            pagingOffset,
            category,
            query
        )
        //then
        coVerify(exactly = 1) {
            mockMercadoLibreService.searchItems(
                siteId,
                itemsPerPage,
                pagingOffset,
                category,
                query
            )
        }
        assertEquals(responseExpected, response)
    }

    @Test
    fun `test should call GetItemDetail service`() = runBlocking {
        //given
        val includeAttributes = "all"
        val responseExpected = Response.success(mockItemDto)
        coEvery {
            mockMercadoLibreService.getItemDetail(
                itemId,
                includeAttributes
            )
        } returns responseExpected
        //when
        val response = searchRemoteDataSourceImpl.getItemDetail(
            itemId,
        )
        //then
        coVerify(exactly = 1) {
            mockMercadoLibreService.getItemDetail(
                itemId,
                includeAttributes
            )
        }
        assertEquals(responseExpected, response)
    }

    @Test
    fun `test should call GetItemDescription service`() = runBlocking {
        //given
        val responseExpected = Response.success(mockItemDescriptionDto)
        coEvery {
            mockMercadoLibreService.getItemDescription(
                itemId
            )
        } returns responseExpected
        //when
        val response = searchRemoteDataSourceImpl.getItemDescription(itemId)
        //then
        coVerify(exactly = 1) { mockMercadoLibreService.getItemDescription(itemId) }
        assertEquals(responseExpected, response)
    }
}