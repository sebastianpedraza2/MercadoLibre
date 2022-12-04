package com.pedraza.sebastian.search_presentation.itemDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.core.di.Dispatcher
import com.pedraza.sebastian.core.di.MercadoLibreDispatchers
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.search_domain.usecases.items.GetItemDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    @Dispatcher(MercadoLibreDispatchers.IO)
    private val meliDispatcher: CoroutineDispatcher,
    private val getItemDetailUseCase: GetItemDetailUseCase,
    private val snackbarManager: SnackbarManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(ItemDetailState())
    val uiState = _uiState.asStateFlow()

    fun getItemDetail(itemId: String) {
        viewModelScope.launch(meliDispatcher) {
            _uiState.update { currentState ->
                currentState.copy(screenState = ScreenUiState.Loading)
            }
            when (val response = getItemDetailUseCase.invoke(itemId)) {
                is Result.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            itemDetail = response.data,
                            screenState = ScreenUiState.Fetched
                        )
                    }
                }
                is Result.Error -> {
                    snackbarManager.showMessage(response.message)
                }
            }
        }
    }
}