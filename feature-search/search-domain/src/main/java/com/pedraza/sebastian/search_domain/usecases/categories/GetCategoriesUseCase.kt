package com.pedraza.sebastian.search_domain.usecases.categories

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.category.Category

interface GetCategoriesUseCase {
    suspend fun invoke(siteId: String): Result<List<Category>>
}