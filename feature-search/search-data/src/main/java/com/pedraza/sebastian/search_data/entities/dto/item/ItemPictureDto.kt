package com.pedraza.sebastian.search_data.entities.dto.item


import com.google.gson.annotations.SerializedName

data class ItemPictureDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("max_size")
    val maxSize: String?,
    @SerializedName("secure_url")
    val secureUrl: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("url")
    val url: String?
)