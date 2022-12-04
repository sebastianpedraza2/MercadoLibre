package com.pedraza.sebastian.mercadolibre.ui.navigation

sealed class Routes(val route: String) {
    object Search : Routes("search")
    object ItemDetail : Routes("itemDetail")

    companion object {
        const val ITEM_DETAIL_ARGUMENT = "itemId"
    }
}
