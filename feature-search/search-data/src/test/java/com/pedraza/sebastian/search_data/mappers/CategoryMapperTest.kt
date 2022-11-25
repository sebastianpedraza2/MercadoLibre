package com.pedraza.sebastian.search_data.mappers

import com.google.common.truth.Truth.assertThat
import com.pedraza.sebastian.search_data.utils.generateMockCategoryDto
import org.junit.Test

class CategoryMapperTest {

    @Test
    fun `test CategoryMapper works correctly`() {
        //given
        val categoryDto = generateMockCategoryDto(name = "Alimentos y Bebidas")
        //when
        val domainCategory = categoryDto.toDomain()
        //then
        assertThat(domainCategory.id).isNotNull()
        assertThat(domainCategory.name).isEqualTo("Alimentos y Bebidas")
    }

    @Test
    fun `test CategoryMapper when name is null`() {
        //given
        val categoryDto = generateMockCategoryDto(name = null)
        //when
        val domainCategory = categoryDto.toDomain()
        //then
        assertThat(domainCategory.id).isNotNull()
        assertThat(domainCategory.name).isNotNull()
    }
}