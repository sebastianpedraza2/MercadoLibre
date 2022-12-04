package com.pedraza.sebastian.core.utils

import java.text.DecimalFormat
import java.text.NumberFormat

fun formatPrice(amount: Double): String =
    DecimalFormat("###,###,##0.00").format(amount) ?: ""

fun formatPriceWithCurrency(price: Double): String =
    NumberFormat.getCurrencyInstance().also {
        it.maximumFractionDigits = 2
    }.format(price)
