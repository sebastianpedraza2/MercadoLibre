package com.pedraza.sebastian.search_presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.core.di.Dispatcher
import com.pedraza.sebastian.core.di.MercadoLibreDispatchers
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_PAGE_SIZE
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_SITE_ID
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.usecases.categories.GetCategoriesUseCase
import com.pedraza.sebastian.search_domain.usecases.items.SearchItemsUseCase
import com.pedraza.sebastian.search_presentation.utils.PaginationFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @Dispatcher(MercadoLibreDispatchers.IO)
    private val meliDispatcher: CoroutineDispatcher,
    private val searchItemsUseCase: SearchItemsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val paginationFactory: PaginationFactory,
    private val snackbarManager: SnackbarManager
) : ViewModel() {

    /**
     * Controls the app state of search screen
     */
    private val _uiState = MutableStateFlow(SearchState())
    val uiState = _uiState.asStateFlow()

    /**
     * Controls the search query
     */
    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    /**
     * Controls the content that is displayed on screen
     */
    val searchDisplay = combine(_uiState, _query) { uiState, query ->
        when {
            !uiState.isFocused && query.isEmpty() -> SearchDisplay.Categories
            uiState.isFocused && query.isEmpty() -> SearchDisplay.Suggestions
            query.isNotEmpty() -> SearchDisplay.Results
            else -> SearchDisplay.Categories
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        SearchDisplay.Categories
    )

    /**
     * Api that provides pagination functionality
     */
    private val meliPaginator by lazy {
        paginationFactory.getSearchPaginator(
            initialOffset = _uiState.value.offset,
            onLoadUpdated = ::updateLoadingState,
            onRequest = ::requestNewItems,
            getNextOffset = ::getNextOffset,
            onError = ::showSnackBar,
            onSuccess = ::updateItemsState
        )
    }

    init {
        setupSearchDebounce()
    }

    /**
     * ItemViewModel events source of truth
     */

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearchItems -> setSearchQuery(event.query)
            is SearchEvent.OnLoadNewItems -> loadNewItems()
            is SearchEvent.OnItemClicked -> navigateToItemDetail(event.itemId)
            is SearchEvent.OnFocusChanged -> updateFocus(event.focus)
            is SearchEvent.OnClearQuery -> clearQuery()
            is SearchEvent.OnGetCategories -> getCategories()
        }
    }

    private fun getCategories() {
        viewModelScope.launch(meliDispatcher) {
            _uiState.update { currentState ->
                currentState.copy(screenUiState = ScreenUiState.Loading)
            }
            when (val response = getCategoriesUseCase.invoke(DEFAULT_SITE_ID)) {
                is Result.Success -> _uiState.update { currentState ->
                    currentState.copy(
                        categories = response.value,
                        screenUiState = ScreenUiState.Fetched
                    )
                }
                is Result.Error -> {
                    snackbarManager.showMessage(response.message)
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchDebounce() {
        _query.filterNot { query ->
            query.isEmpty()
        }
            .onEach {
                resetResultItems()
            }
            .debounce(DEBOUNCE_TIMEOUT)
            .onEach {
                loadNewItems()
            }
            .launchIn(viewModelScope)
    }

    private fun clearQuery() {
        _query.update { "" }
    }

    private fun updateFocus(focus: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isFocused = focus
            )
        }
    }

    private fun navigateToItemDetail(itemId: String) {
        //TODO
    }

    private fun updateItemsState(response: SearchResult, newOffset: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                offset = newOffset,
                items = currentState.items + response.results,
                endReached = hasReachedEnd(response),
                primaryResults = response.primaryResults
            )
        }
    }

    private fun getNextOffset(response: SearchResult): Int =
        with(response) { pagingOffset + itemsPerPage }

    private fun updateLoadingState(state: Boolean) {
        _uiState.update { currentState -> currentState.copy(isSearching = state) }
    }

    private suspend fun requestNewItems(nextPage: Int): Result<SearchResult> =
        searchItemsUseCase.invoke(
            DEFAULT_SITE_ID,
            DEFAULT_PAGE_SIZE,
            nextPage,
            _query.value
        )


    private fun hasReachedEnd(response: SearchResult): Boolean {
        return (response.pagingOffset <= response.primaryResults) || (response.pagingOffset + response.itemsPerPage < 1000)
    }

    private fun setSearchQuery(query: String) {
        _query.value = query
    }

    private fun loadNewItems() {
        viewModelScope.launch(meliDispatcher) {
            meliPaginator.loadNextItems()
        }
    }

    private fun resetResultItems() {
        meliPaginator.reset()
        _uiState.update { currentState -> currentState.copy(items = emptyList(), offset = 0) }
    }

    private fun showSnackBar(message: UiText) {
        snackbarManager.showMessage(message)
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 900L
    }
}