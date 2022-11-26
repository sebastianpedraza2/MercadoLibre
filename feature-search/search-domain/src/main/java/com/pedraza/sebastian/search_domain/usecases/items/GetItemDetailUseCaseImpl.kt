package com.pedraza.sebastian.search_domain.usecases.items

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.item.Item
import com.pedraza.sebastian.search_domain.repository.SearchRepository

class GetItemDetailUseCaseImpl(private val repository: SearchRepository) : GetItemDetailUseCase {
    override suspend fun invoke(itemId: String): Result<Item> = repository.getItemDetail(itemId)
}