package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName

data class ItemBodyDto(
    @SerializedName("attributes")
    val attributes: List<AttributeDto>,
    @SerializedName("available_quantity")
    val availableQuantity: Int,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("descriptions")
    val descriptions: List<Any>,
    @SerializedName("id")
    val id: String,
    @SerializedName("initial_quantity")
    val initialQuantity: Int,
    @SerializedName("permalink")
    val permalink: String,
    @SerializedName("pictures")
    val pictures: List<ItemPictureDto>,
    @SerializedName("price")
    val price: Int,
    @SerializedName("sold_quantity")
    val soldQuantity: Int,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("warranty")
    val warranty: String
)