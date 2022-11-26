package com.pedraza.sebastian.search_domain.usecases.items

import com.pedraza.sebastian.core.utils.Result
import com.pedraza.sebastian.search_domain.models.item.Item

interface GetItemDetailUseCase {
    suspend fun invoke(itemId: String): Result<Item>
}