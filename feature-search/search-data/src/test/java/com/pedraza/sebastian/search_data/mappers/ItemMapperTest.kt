package com.pedraza.sebastian.search_data.mappers

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_data.entities.dto.item.AttributeDto
import com.pedraza.sebastian.search_data.entities.dto.item.ItemDescriptionDto
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
        )

        val itemDescription =
            ItemDescriptionDto(plainText = "SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")

        //when
        val domainItem = itemDto.toDomain(itemDescription)

        //then
        assertThat(domainItem.pictures).isEqualTo(listOf("https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"))
        assertThat(domainItem.description).isEqualTo("SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")
        assertThat(domainItem.itemCondition).isEqualTo("Nuevo")
        assertThat(domainItem.id).isEqualTo("MCO462614986")
        assertThat(domainItem.availableQuantity).isEqualTo(1)
        assertThat(domainItem.price).isEqualTo(33950.0)
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
        )

        val itemDescription =
            ItemDescriptionDto(plainText = "SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")

        //when
        val domainItem = itemDto.toDomain(itemDescription)

        //then
        assertThat(domainItem.pictures).isEqualTo(listOf("https://http2.mlstatic.com/D_955993-MCO43399175828_092020-O.jpg"))
        assertThat(domainItem.description).isEqualTo("SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")
        assertThat(domainItem.itemCondition).isNotNull()
    }

    @Test
    fun `test ItemMapper when pictures is null`() {
        //given
        val itemDto = generateMockItemDto(
            pictures = null,
            attributes = null,
        )

        val itemDescription =
            ItemDescriptionDto(plainText = "SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")

        //when
        val domainItem = itemDto.toDomain(itemDescription)

        //then
        assertThat(domainItem.title).isNotNull()
        assertThat(domainItem.pictures).isNotNull()
        assertThat(domainItem.itemCondition).isNotNull()
        assertThat(domainItem.description).isEqualTo("SOMOS MOBILEWAVE \n\nCABLE ORIGINAL APPLE \nMODELOS \nIPHONE 5,6,7,8,X,XS,XR,11\nCOLOR BLANCO \nEMPAQUE BULK \n\n\nENVIO")
    }


    @Test
    fun `test ItemMapper when description is null`() {
        //given
        val itemDto = generateMockItemDto(
            pictures = null,
            attributes = null,
        )

        val itemDescription =
            ItemDescriptionDto(plainText = null)

        //when
        val domainItem = itemDto.toDomain(itemDescription)

        //then
        assertThat(domainItem.title).isNotNull()
        assertThat(domainItem.pictures).isNotNull()
        assertThat(domainItem.itemCondition).isNotNull()
        assertThat(domainItem.description).isEmpty()
    }
}