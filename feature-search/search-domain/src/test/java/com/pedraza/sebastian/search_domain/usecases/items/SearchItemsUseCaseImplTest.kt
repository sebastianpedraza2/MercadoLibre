package com.pedraza.sebastian.search_domain.usecases.items

import com.google.common.truth.Truth
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchItemsUseCaseImplTest {

    private val mockSearchRepository: SearchRepository = mockk(relaxed = true)
    private val mockDomainSearchResult: SearchResult = mockk(relaxed = true)
    private val mockErrorMessage: UiText = mockk(relaxed = true)
    private lateinit var searchItemsUseCaseImpl: SearchItemsUseCaseImpl
    private val siteId = "MCO"
    private val itemsPerPage = 10
    private val pagingOffset = 0
    private val query = ""

    @Before
    fun setUp() {
        searchItemsUseCaseImpl = SearchItemsUseCaseImpl(mockSearchRepository)
    }

    @Test
    fun `test SearchItemsUseCase should invoke Repository with Success result`() =
        runBlocking {
            //given
            coEvery {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = query,
                    category = null
                )
            } returns Result.Success(
                mockDomainSearchResult
            )
            //when
            val response = searchItemsUseCaseImpl.invoke(
                siteId,
                itemsPerPage,
                pagingOffset,
                query
            )
            //then
            coVerify(exactly = 1) {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = query,
                    category = null
                )
            }
            Truth.assertThat(response is Result.Success).isTrue()
        }

    @Test
    fun `test SearchItemsUseCase should invoke Repository with Error result`() =
        runBlocking {
            //given
            coEvery {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = query,
                    category = null
                )
            } returns Result.Error(message = mockErrorMessage)
            //when
            val response = searchItemsUseCaseImpl.invoke(
                siteId,
                itemsPerPage,
                pagingOffset,
                query
            )
            //then
            coVerify(exactly = 1) {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = query,
                    category = null
                )
            }
            Truth.assertThat(response is Result.Error).isTrue()
            Truth.assertThat(response.message).isEqualTo(mockErrorMessage)
        }
}
