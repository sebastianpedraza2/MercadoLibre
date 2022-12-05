package com.pedraza.sebastian.search_domain.usecases.suggestions

import com.pedraza.sebastian.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface GetSearchSuggestionsUseCase {
    fun invoke(): Result<Flow<List<String>>>
}
