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

class GetItemsByCategoryUseCaseImplTest {

    private val mockSearchRepository: SearchRepository = mockk(relaxed = true)
    private val mockDomainSearchResult: SearchResult = mockk(relaxed = true)
    private val mockErrorMessage: UiText = mockk(relaxed = true)
    private lateinit var getItemsByCategoryUseCaseImpl: GetItemsByCategoryUseCaseImpl
    private val siteId = "MCO"
    private val itemsPerPage = 10
    private val pagingOffset = 0
    private val category = ""

    @Before
    fun setUp() {
        getItemsByCategoryUseCaseImpl = GetItemsByCategoryUseCaseImpl(mockSearchRepository)
    }

    @Test
    fun `test GetItemsByCategoryUseCase should invoke Repository with Success result`() =
        runBlocking {
            //given
            coEvery {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = null,
                    category = category
                )
            } returns Result.Success(
                mockDomainSearchResult
            )
            //when
            val response = getItemsByCategoryUseCaseImpl.invoke(
                siteId,
                itemsPerPage,
                pagingOffset,
                category
            )
            //then
            coVerify(exactly = 1) {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = null,
                    category = category
                )
            }
            Truth.assertThat(response is Result.Success).isTrue()
        }

    @Test
    fun `test GetItemsByCategoryUseCase should invoke Repository with Error result`() =
        runBlocking {
            //given
            coEvery {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = null,
                    category = category
                )
            } returns Result.Error(message = mockErrorMessage)
            //when
            val response = getItemsByCategoryUseCaseImpl.invoke(
                siteId,
                itemsPerPage,
                pagingOffset,
                category
            )
            //then
            coVerify(exactly = 1) {
                mockSearchRepository.searchItems(
                    siteId = siteId,
                    itemsPerPage = itemsPerPage,
                    pagingOffset = pagingOffset,
                    query = null,
                    category = category
                )
            }
            Truth.assertThat(response is Result.Error).isTrue()
            Truth.assertThat(response.message).isEqualTo(mockErrorMessage)
        }
}
