package com.pedraza.sebastian.mercadolibre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import com.pedraza.sebastian.android_helpers.snackbar.SnackbarManager
import com.pedraza.sebastian.mercadolibre.ui.components.MercadoLibreScaffold
import com.pedraza.sebastian.mercadolibre.ui.navigation.Routes
import com.pedraza.sebastian.mercadolibre.ui.navigation.meliNavGraph
import com.pedraza.sebastian.mercadolibre.ui.theme.MercadoLibreTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var snackbarManager: SnackbarManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercadoLibreTheme {
                val appState = rememberMeliAppState(snackbarManager = snackbarManager)
                MercadoLibreScaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = it,
                            modifier = Modifier.systemBarsPadding(),
                            snackbar = { snackbarData -> Snackbar(snackbarData) }
                        )
                    },
                    scaffoldState = appState.scaffoldState
                ) { innerPaddingModifier ->
                    NavHost(
                        navController = appState.navController,
                        startDestination = Routes.Search.route,
                        modifier = Modifier.padding(innerPaddingModifier)
                    ) {
                        meliNavGraph(
                            onItemSelected = appState::navigateToItemDetail,
                            upPress = appState::upPress
                        )
                    }
                }
            }
        }
    }
}