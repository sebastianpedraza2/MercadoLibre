package com.pedraza.sebastian.search_domain.usecases.suggestions

import com.pedraza.sebastian.core.utils.Result

interface SaveSearchSuggestionUseCase {
    suspend fun invoke(previousList: List<String>, suggestion: String): Result<Unit>
}
