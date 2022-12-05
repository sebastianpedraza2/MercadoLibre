package com.pedraza.sebastian.search_domain.usecases.categories

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.search_domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import org.junit.Before

class GetCategoriesUseCaseImplTest {

    private val mockSearchRepository: SearchRepository = mockk(relaxed = true)
    private val mockDomainCategoryList: List<Category> = mockk(relaxed = true)
    private val mockErrorMessage: UiText = mockk(relaxed = true)
    private lateinit var getDetailMovieUseCaseImpl: GetCategoriesUseCaseImpl
    private val siteId = "MCO"

    @Before
    fun setUp() {
        getDetailMovieUseCaseImpl = GetCategoriesUseCaseImpl(mockSearchRepository)
    }

    @Test
    fun `test GetCategoriesUseCase should invoke Repository with Success result`() = runBlocking {
        //given
        coEvery { mockSearchRepository.getCategories(siteId) } returns Result.Success(
            mockDomainCategoryList
        )
        //when
        val response = getDetailMovieUseCaseImpl.invoke(siteId)
        //then
        coVerify(exactly = 1) { mockSearchRepository.getCategories(siteId) }
        assertThat(response is Result.Success).isTrue()
    }

    @Test
    fun `test GetCategoriesUseCase should invoke Repository with Error result`() = runBlocking {
        //given
        coEvery { mockSearchRepository.getCategories(siteId) } returns Result.Error(message = mockErrorMessage)
        //when
        val response = getDetailMovieUseCaseImpl.invoke(siteId)
        //then
        coVerify(exactly = 1) { mockSearchRepository.getCategories(siteId) }
        assertThat(response is Result.Error).isTrue()
        assertThat(response.message).isEqualTo(mockErrorMessage)
    }
}
