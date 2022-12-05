package com.pedraza.sebastian.search_domain.usecases.items

import com.google.common.truth.Truth
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.item.Item
import com.pedraza.sebastian.search_domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetItemDetailUseCaseImplTest {

    private val mockSearchRepository: SearchRepository = mockk(relaxed = true)
    private val mockDomainItem: Item = mockk(relaxed = true)
    private val mockErrorMessage: UiText = mockk(relaxed = true)
    private lateinit var getItemDetailUseCaseImpl: GetItemDetailUseCaseImpl
    private val itemId = "MCO1073403941"

    @Before
    fun setUp() {
        getItemDetailUseCaseImpl = GetItemDetailUseCaseImpl(mockSearchRepository)
    }

    @Test
    fun `test GetItemDetailUseCase should invoke Repository with Success result`() = runBlocking {
        //given
        coEvery { mockSearchRepository.getItemDetail(itemId) } returns Result.Success(
            mockDomainItem
        )
        //when
        val response = getItemDetailUseCaseImpl.invoke(itemId)
        //then
        coVerify(exactly = 1) { mockSearchRepository.getItemDetail(itemId) }
        Truth.assertThat(response is Result.Success).isTrue()
    }

    @Test
    fun `test GetItemDetailUseCase should invoke Repository with Error result`() = runBlocking {
        //given
        coEvery { mockSearchRepository.getItemDetail(itemId) } returns Result.Error(message = mockErrorMessage)
        //when
        val response = getItemDetailUseCaseImpl.invoke(itemId)
        //then
        coVerify(exactly = 1) { mockSearchRepository.getItemDetail(itemId) }
        Truth.assertThat(response is Result.Error).isTrue()
        Truth.assertThat(response.message).isEqualTo(mockErrorMessage)
    }
}
