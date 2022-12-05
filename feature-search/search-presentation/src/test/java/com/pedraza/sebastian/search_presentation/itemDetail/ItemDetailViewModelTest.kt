package com.pedraza.sebastian.search_presentation.itemDetail

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.search_domain.usecases.items.GetItemDetailUseCase
import com.pedraza.sebastian.search_presentation.MainCoroutineRule
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.item.Item
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemDetailViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var itemDetailViewModel: ItemDetailViewModel
    private val getItemDetailUseCase: GetItemDetailUseCase = mockk()
    private val snackbarManager: SnackbarManager = mockk()
    private val mockDomainItem: Item = mockk(relaxed = true)
    private val mockErrorMessage: UiText = mockk(relaxed = true)
    private val itemId = "MCO1073403941"

    @Before
    fun setup() {
        itemDetailViewModel = ItemDetailViewModel(
            mainCoroutineRule.testDispatcher,
            getItemDetailUseCase,
            snackbarManager
        )
    }

    @Test
    fun `test itemDetailViewModel should call GetDetailMovieUseCase`() {
        //given
        coEvery { getItemDetailUseCase.invoke(itemId) } returns Result.Success(mockDomainItem)
        //when
        itemDetailViewModel.getItemDetail(itemId)
        //then
        coVerify { getItemDetailUseCase.invoke(itemId) }
    }

    @Test
    fun `test itemDetailViewModel when getItemDetail returns Success result`() {
        //given
        coEvery { getItemDetailUseCase.invoke(itemId) } returns Result.Success(mockDomainItem)
        assertThat(itemDetailViewModel.uiState.value.screenState is ScreenUiState.Init).isTrue()
        assertThat(itemDetailViewModel.uiState.value.itemDetail).isEqualTo(Item())
        //when
        itemDetailViewModel.getItemDetail(itemId)
        //then
        assertThat(itemDetailViewModel.uiState.value.screenState is ScreenUiState.Fetched).isTrue()
        assertThat(itemDetailViewModel.uiState.value.itemDetail).isEqualTo(mockDomainItem)
    }

    @Test
    fun `test itemDetailViewModel when getItemDetail returns Error result`() {
        //given
        coEvery { getItemDetailUseCase.invoke(itemId) } returns Result.Error(message = mockErrorMessage)
        assertThat(itemDetailViewModel.uiState.value.screenState is ScreenUiState.Init).isTrue()
        assertThat(itemDetailViewModel.uiState.value.itemDetail).isEqualTo(Item())
        every { snackbarManager.showMessage(mockErrorMessage) } just Runs
        //when
        itemDetailViewModel.getItemDetail(itemId)
        //then
        assertThat(itemDetailViewModel.uiState.value.screenState is ScreenUiState.Loading).isTrue()
        verify(exactly = 1) { snackbarManager.showMessage(mockErrorMessage) }
    }
}
