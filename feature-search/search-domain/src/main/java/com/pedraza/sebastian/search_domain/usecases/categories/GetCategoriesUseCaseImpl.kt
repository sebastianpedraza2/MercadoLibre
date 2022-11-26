package com.pedraza.sebastian.search_domain.usecases.categories

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.category.Category
import com.pedraza.sebastian.search_domain.repository.SearchRepository

class GetCategoriesUseCaseImpl(private val repository: SearchRepository) : GetCategoriesUseCase {
    override suspend fun invoke(siteId: String): Result<List<Category>> =
        repository.getCategories(siteId)
}