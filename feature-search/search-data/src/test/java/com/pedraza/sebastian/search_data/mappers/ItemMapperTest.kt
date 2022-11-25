package com.pedraza.sebastian.search_data.mappers

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_data.entities.dto.item.AttributeDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemPictureDto
import com.pedraza.sebastian.search_data.utils.generateMockItemDto
import org.junit.Test

class ItemMapperTest {

    @Test
    fun `test ItemMapper works correctly`() {
        //given
        val itemDto = generateMockItemDto(
            listOf(
                ItemPictureDto(
                    id = "1",
                    maxSize = "757x829",
                    secureUrl = "https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg",
                    size = "456x500",
                    url = "https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"
                ),
            ),
            attributes = listOf(
                AttributeDto(id = "MODEL", name = "Modelo", valueName = "MD818ZM"),
                AttributeDto(
                    id = "COMPATIBLE_PRODUCT_MODELS",
                    name = "Modelos de productos compatibles",
                    valueName = "iPhone 13"
                ),
                AttributeDto(
                    id = "ITEM_CONDITION",
                    name = "Condición del ítem",
                    valueName = "Nuevo"
                )
            ),
            code = 200
        )
        //when
        val domainItem = itemDto.toDomain()

        //then
        assertThat(domainItem.pictures).isEqualTo(listOf("https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"))
        assertThat(domainItem.itemCondition).isEqualTo("Nuevo")
    }

    @Test
    fun `test ItemMapper when attributes is null`() {
        //given
        val itemDto = generateMockItemDto(
            listOf(
                ItemPictureDto(
                    id = "1",
                    maxSize = null,
                    secureUrl = "https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg",
                    size = null,
                    url = "https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"
                ),
            ),
            attributes = null,
            code = 200
        )
        //when
        val domainItem = itemDto.toDomain()

        //then
        assertThat(domainItem.pictures).isEqualTo(listOf("https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"))
        assertThat(domainItem.itemCondition).isNotNull()
    }

    @Test
    fun `test ItemMapper when pictures is null`() {
        //given
        val itemDto = generateMockItemDto(
            pictures = null,
            attributes = null,
            code = 200
        )
        //when
        val domainItem = itemDto.toDomain()

        //then
        assertThat(domainItem.title).isNotNull()
        assertThat(domainItem.pictures).isNotNull()
        assertThat(domainItem.itemCondition).isNotNull()
        assertThat(domainItem.description).isEmpty()
    }
}