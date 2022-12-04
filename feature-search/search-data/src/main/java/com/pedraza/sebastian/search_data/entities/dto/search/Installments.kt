package com.pedraza.sebastian.search_data.entities.dto.search

import com.google.gson.annotations.SerializedName

data class Installments(
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("amount")
    val amount: Double?
)
