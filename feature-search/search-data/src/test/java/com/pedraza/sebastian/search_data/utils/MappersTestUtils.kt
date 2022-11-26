package com.pedraza.sebastian.search_data.utils

import com.pedraza.sebastian.search_data.entities.dto.category.CategoryDto
import com.pedraza.sebastian.search_data.entities.dto.item.AttributeDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemPictureDto
import com.pedraza.sebastian.search_data.entities.dto.search.PagingDto
import com.pedraza.sebastian.search_data.entities.dto.search.QueryResultDto
import com.pedraza.sebastian.search_data.entities.dto.search.SearchDto


fun generateMockItemDto(
    pictures: List<ItemPictureDto>?,
    attributes: List<AttributeDto>?,
) = ItemDto(
    id = "MCO462614986",
    attributes = attributes,
    availableQuantity = 1,
    categoryId = null,
    initialQuantity = null,
    permalink = null,
    pictures = pictures,
    price = 33950,
    soldQuantity = null,
    secureThumbnail = null,
    title = null,
    warranty = null
)


fun generateMockSearchDto(results: List<QueryResultDto>?): SearchDto =
    SearchDto(
        paging = PagingDto(limit = 1, offset = 0, primaryResults = 50, total = 50),
        results = results,
        siteId = null
    )

fun generateMockCategoryDto(name: String?) = CategoryDto(id = "1", name = name)
