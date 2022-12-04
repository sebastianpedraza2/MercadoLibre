package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName
import com.pedraza.sebastian.search_data.entities.dto.search.Installments

data class ItemDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("attributes")
    val attributes: List<AttributeDto>?,
    @SerializedName("available_quantity")
    val availableQuantity: Int?,
    @SerializedName("category_id")
    val categoryId: String?,
    @SerializedName("initial_quantity")
    val initialQuantity: Int?,
    @SerializedName("permalink")
    val permalink: String?,
    @SerializedName("pictures")
    val pictures: List<ItemPictureDto>?,
    @SerializedName("price")
    val price: Double,
    @SerializedName("sold_quantity")
    val soldQuantity: Int?,
    @SerializedName("secure_thumbnail")
    val secureThumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("warranty")
    val warranty: String?
)