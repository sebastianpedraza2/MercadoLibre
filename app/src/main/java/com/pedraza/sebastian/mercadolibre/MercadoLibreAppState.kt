package com.pedraza.sebastian.mercadolibre

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.core.utils.UiText
import com.pedraza.sebastian.mercadolibre.ui.navigation.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun rememberMeliAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        MeliAppState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

/**
 * Responsible for holding state related to [MeliAppState] and containing UI-related logic.
 */
@Stable
class MeliAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    // Process snackbars coming from SnackbarManager
    init {
        coroutineScope.launch {
            snackbarManager.messages.collect { currentMessages ->
                if (currentMessages.isNotEmpty()) {
                    val currentMessage = currentMessages[0]

                    val text = when (val message = currentMessage.message) {
                        is UiText.DynamicString -> message.text
                        is UiText.ResourcesString -> resources.getText(message.resId)
                    }

                    // Display the snackbar on the screen. `showSnackbar` is a function
                    // that suspends until the snackbar disappears from the screen
                    scaffoldState.snackbarHostState.showSnackbar(text.toString())
                    // Once the snackbar is gone or dismissed, notify the SnackbarManager
                    snackbarManager.setMessageShown(currentMessage.id)
                }
            }
        }
    }
    // ----------------------------------------------------------
    // Navigation state source of truth
    // ----------------------------------------------------------

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToItemDetail(itemId: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${Routes.ItemDetail.route}/$itemId")
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

/**
 * A composable function that returns the [Resources]. It will be recomposed when `Configuration`
 * gets updated.
 */
@Composable
@ReadOnlyComposable
private fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}
