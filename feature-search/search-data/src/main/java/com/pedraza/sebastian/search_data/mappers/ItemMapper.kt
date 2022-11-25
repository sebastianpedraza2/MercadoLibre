package com.pedraza.sebastian.search_data.mappers

import com.pedraza.sebastian.core.utils.Constants.ITEM_CONDITION
import com.pedraza.sebastian.search_data.entities.dto.item.AttributeDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemPictureDto
import com.pedraza.sebastian.search_domain.models.item.Item

fun ItemDto.toDomain(): Item {
    return with(body) {
        Item(
            id = id,
            title = title.orEmpty(),
            description = "",
            itemCondition = getItemCondition(attributes).orEmpty(),
            availableQuantity = availableQuantity,
            initialQuantity = initialQuantity,
            pictures = getItemPictures(pictures).orEmpty(),
            price = price,
            soldQuantity = soldQuantity,
            thumbnail = thumbnail,
            warranty = warranty,
            categoryId = categoryId,
        )
    }
}

fun getItemCondition(attributes: List<AttributeDto>?): String? {
    return attributes?.let {
        it.find { attribute ->
            attribute.id == ITEM_CONDITION
        }?.valueName
    }
}

fun getItemPictures(pictures: List<ItemPictureDto>?): List<String>? {
    return pictures?.let {
        it.mapNotNull { picture -> picture.secureUrl }
    }
}
