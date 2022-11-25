package com.pedraza.sebastian.search_data.mappers

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_domain.models.category.Category

fun CategoryDto.toDomain(): Category = Category(
    id = id,
    name = name.orEmpty()
)