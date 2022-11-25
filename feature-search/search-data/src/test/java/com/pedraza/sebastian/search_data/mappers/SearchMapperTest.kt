package com.pedraza.sebastian.search_data.mappers

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_data.entities.dto.search.QueryResultDto
import com.pedraza.sebastian.search_data.utils.generateMockSearchDto
import org.junit.Test

class SearchMapperTest {

    @Test
    fun `test SearchMapper works correctly`() {
        //given
        val searchDto = generateMockSearchDto(
            results = listOf(
                QueryResultDto(
                    availableQuantity = 1,
                    itemId = "MCO462381052",
                    price = 33454,
                    soldQuantity = 1,
                    thumbnail = "http://http2.mlstatic.com/D_818576-MLA45731625708_042021-I.jpg",
                    title = "Cargador iPhone 7,8/8+,x,xs,max,xr Original"
                )
            )
        )
        //when
        val domainSearch = searchDto.toDomain()

        //then
        assertThat(domainSearch.itemsPerPage).isEqualTo(1)
        assertThat(domainSearch.pagingOffset).isEqualTo(0)
        with(domainSearch.results[0]) {
            assertThat(price).isEqualTo(33454)
            assertThat(itemId).isEqualTo("MCO462381052")
        }
    }

    @Test
    fun `test SearchMapper when results is empty`() {
        //given
        val searchDto = generateMockSearchDto(
            results = emptyList()
        )
        //when
        val domainSearch = searchDto.toDomain()

        //then
        assertThat(domainSearch.itemsPerPage).isEqualTo(1)
        assertThat(domainSearch.pagingOffset).isEqualTo(0)
        assertThat(domainSearch.results).isNotNull()
        assertThat(domainSearch.results).isEmpty()
    }

    @Test
    fun `test SearchMapper when results is null`() {
        //given
        val searchDto = generateMockSearchDto(
            results = null
        )
        //when
        val domainSearch = searchDto.toDomain()

        //then
        assertThat(domainSearch.results).isNotNull()
    }

    @Test
    fun `test SearchMapper with null values`() {
        //given
        val searchDto = generateMockSearchDto(
            results = listOf(
                QueryResultDto(
                    availableQuantity = null,
                    itemId = "MCO462381052",
                    price = 33454,
                    soldQuantity = null,
                    thumbnail = null,
                    title = "Cargador iPhone 7,8/8+,x,xs,max,xr Original"
                )
            )
        )
        //when
        val domainSearch = searchDto.toDomain()

        //then
        with(domainSearch.results[0]) {
            assertThat(availableQuantity).isNull()
            assertThat(soldQuantity).isNull()
            assertThat(thumbnail).isNotNull()
        }
    }
}