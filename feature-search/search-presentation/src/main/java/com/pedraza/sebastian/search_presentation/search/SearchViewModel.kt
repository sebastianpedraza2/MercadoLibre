package com.pedraza.sebastian.search_presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_PAGE_SIZE
import com.pedraza.sebastian.core.utils.Constants.DEFAULT_SITE_ID
import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.core.utils.ScreenUiState
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.core.R
import com.pedraza.sebastian.search_domain.models.search.SearchResult
import com.pedraza.sebastian.search_domain.usecases.categories.GetCategoriesUseCase
import com.pedraza.sebastian.search_domain.usecases.items.SearchItemsUseCase
import com.pedraza.sebastian.search_domain.usecases.suggestions.GetSearchSuggestionsUseCase
import com.pedraza.sebastian.search_domain.usecases.suggestions.SaveSearchSuggestionUseCase
import com.pedraza.sebastian.search_presentation.utils.PaginationFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val meliContext: CoroutineContext,
    private val searchItemsUseCase: SearchItemsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val saveSearchSuggestionUseCase: SaveSearchSuggestionUseCase,
    private val getSearchSuggestionsUseCase: GetSearchSuggestionsUseCase,
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
            shouldDisplayCategories(uiState, query) -> SearchDisplay.Categories
            shouldDisplaySuggestions(uiState, query) -> SearchDisplay.Suggestions
            shouldDisplayNoResults(uiState, query) -> SearchDisplay.NoResults
            shouldDisplayResults(query) -> SearchDisplay.Results
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
            onLoadUpdated = ::updatePagingSearchingState,
            onRequest = ::requestNewItems,
            getNextOffset = ::getNextOffset,
            onError = ::showSnackBar,
            onSuccess = ::onSearchSuccessful
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
            is SearchEvent.OnFocusChanged -> updateFocus(event.focus)
            is SearchEvent.OnClearQuery -> clearQuery()
            is SearchEvent.OnGetCategories -> getCategories()
            is SearchEvent.OnCategoryClicked -> searchByCategory()
            is SearchEvent.OnSuggestionClicked -> setSearchQuery(event.suggestion)
            is SearchEvent.OnGetSuggestions -> getSuggestions()
        }
    }

    private fun getSuggestions() {
        viewModelScope.launch(meliContext) {
            _uiState.update { currentState ->
                currentState.copy(
                    suggestionsScreenState = ScreenUiState.Loading
                )
            }
            when (val response = getSearchSuggestionsUseCase.invoke()) {
                is Result.Success -> response.data.collect {
                    _uiState.update { currentState ->
                        currentState.copy(
                            suggestions = it,
                            suggestionsScreenState = ScreenUiState.Fetched
                        )
                    }
                }
                is Result.Error -> showSnackBar(response.message)
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch(meliContext) {
            _uiState.update { currentState ->
                currentState.copy(categoriesScreenState = ScreenUiState.Loading)
            }
            when (val response = getCategoriesUseCase.invoke(DEFAULT_SITE_ID)) {
                is Result.Success -> _uiState.update { currentState ->
                    currentState.copy(
                        categories = response.data,
                        categoriesScreenState = ScreenUiState.Fetched
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
                updateSearchScreenState(ScreenUiState.Loading)
            }
            .debounce(DEBOUNCE_TIMEOUT)
            .onEach {
                loadNewItems()
            }
            .launchIn(viewModelScope)
    }

    private fun shouldDisplayCategories(uiState: SearchState, query: String) =
        !uiState.isFocused && query.isEmpty()

    private fun shouldDisplaySuggestions(uiState: SearchState, query: String) =
        uiState.isFocused && query.isEmpty()

    private fun shouldDisplayNoResults(uiState: SearchState, query: String) =
        uiState.resultsScreenState == ScreenUiState.Fetched && query.isNotEmpty() && uiState.primaryResults == NO_PRIMARY_RESULTS

    private fun shouldDisplayResults(query: String) =
        query.isNotEmpty()

    private fun clearQuery() {
        _query.update { "" }
    }

    private fun updateSearchScreenState(screenState: ScreenUiState) {
        _uiState.update { currentState -> currentState.copy(resultsScreenState = screenState) }
    }

    private fun updateFocus(focus: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isFocused = focus
            )
        }
    }

    private fun onSearchSuccessful(response: SearchResult, newOffset: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                offset = newOffset,
                items = currentState.items + response.results,
                endReached = hasReachedEnd(response),
                primaryResults = response.primaryResults,
                resultsScreenState = ScreenUiState.Fetched
            )
        }
        saveQueryToSuggestions(query.value)
    }

    private fun saveQueryToSuggestions(query: String) {
        viewModelScope.launch(meliContext) {
            when (val response = saveSearchSuggestionUseCase.invoke(
                previousList = uiState.value.suggestions,
                suggestion = query
            )) {
                is Result.Error -> showSnackBar(response.message)
                is Result.Success -> Unit
            }
        }
    }

    private fun getNextOffset(response: SearchResult): Int =
        with(response) { pagingOffset + itemsPerPage }

    private fun updatePagingSearchingState(state: Boolean) {
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
        viewModelScope.launch(meliContext) {
            meliPaginator.loadNextItems()
        }
    }

    private fun resetResultItems() {
        meliPaginator.reset()
        _uiState.update { currentState -> currentState.copy(items = emptyList(), offset = 0) }
    }

    private fun searchByCategory() {
        // Todo invoke searchByCategoryUseCase
        showSnackBar(UiText.ResourcesString(R.string.feature_under_work))
    }

    private fun showSnackBar(message: UiText) {
        snackbarManager.showMessage(message)
    }

    companion object {
        const val DEBOUNCE_TIMEOUT = 1000L
        const val TAG = "SearchViewModel"
        const val NO_PRIMARY_RESULTS = 0
    }
}
