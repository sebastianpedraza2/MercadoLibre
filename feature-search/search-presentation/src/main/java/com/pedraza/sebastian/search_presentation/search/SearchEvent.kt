package com.pedraza.sebastian.search_presentation.search

sealed interface SearchEvent {
    data class OnSearchItems(val query: String) : SearchEvent
    data class OnItemClicked(val itemId: String) : SearchEvent
    data class OnFocusChanged(val focus: Boolean) : SearchEvent
    object OnGetCategories : SearchEvent
    object OnLoadNewItems : SearchEvent
    object OnClearQuery : SearchEvent
}