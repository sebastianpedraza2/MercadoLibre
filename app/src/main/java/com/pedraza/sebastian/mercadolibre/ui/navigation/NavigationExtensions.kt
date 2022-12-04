package com.pedraza.sebastian.mercadolibre.ui.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.pedraza.sebastian.search_presentation.itemDetail.ItemDetailScreen
import com.pedraza.sebastian.search_presentation.search.SearchScreen

fun NavGraphBuilder.meliNavGraph(
    onItemSelected: (String, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    composable(route = Routes.Search.route) { from ->
        SearchScreen(onItemClick = { itemId -> onItemSelected(itemId, from) })
    }
    composable(
        route = "${Routes.ItemDetail.route}/{${Routes.ITEM_DETAIL_ARGUMENT}}",
        arguments = listOf(navArgument(Routes.ITEM_DETAIL_ARGUMENT) {
            type = NavType.StringType
        })
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val itemId = arguments.getString(Routes.ITEM_DETAIL_ARGUMENT)
        ItemDetailScreen(itemId = itemId!!, upPress = upPress)
    }
}