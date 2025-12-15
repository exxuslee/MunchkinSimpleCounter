package com.exxuslee.munchkinsimplecounter.ui.root

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.navigation.asRoute
import com.exxuslee.munchkinsimplecounter.navigation.isPrimaryRoute
import com.exxuslee.munchkinsimplecounter.ui.common.AnimationType
import com.exxuslee.munchkinsimplecounter.ui.common.BottomNavigationBar
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer
import com.exxuslee.munchkinsimplecounter.ui.common.LocalNavController
import com.exxuslee.munchkinsimplecounter.ui.common.LocalPaddingController
import com.exxuslee.munchkinsimplecounter.ui.common.animatedComposable
import com.exxuslee.munchkinsimplecounter.ui.game.GameScreen
import com.exxuslee.munchkinsimplecounter.ui.root.models.Event
import com.exxuslee.munchkinsimplecounter.ui.settings.about.AboutScreen
import com.exxuslee.munchkinsimplecounter.ui.settings.donate.DonateScreen
import com.exxuslee.munchkinsimplecounter.ui.settings.language.LanguageScreen
import com.exxuslee.munchkinsimplecounter.ui.settings.main.SettingsScreen
import com.exxuslee.munchkinsimplecounter.ui.settings.terms.TermsScreen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    viewModel: MainViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.asRoute()
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets.statusBars,
                title = {
                    Text(
                        text = currentRoute?.label() ?: "",
                        style = MaterialTheme.typography.headlineMedium,
                    )
                },
                navigationIcon = {
                    if (currentRoute?.isPrimaryRoute() == false) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    } else {
                        HSpacer(48.dp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                ),
                actions = {
                    if (currentRoute?.isPrimaryRoute() == true) {
                        IconButton(onClick = {
                            viewModel.obtainEvent(Event.MainRoute(Routes.SettingsRoute.MainRoute().route))
                            navController.navigate(Routes.SettingsRoute.MainRoute().route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }) {
                            Icon(
                                painterResource(id = R.drawable.ic_baseline_settings_24),
                                contentDescription = stringResource(R.string.title_settings)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (currentRoute?.isPrimaryRoute() == true) BottomNavigationBar(
                viewState = viewState,
                backStackEntry = backStackEntry,
                navController = navController,
            ) { event ->
                viewModel.obtainEvent(event)
            }
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        CompositionLocalProvider(
            LocalNavController provides navController,
            LocalPaddingController provides padding,
        ) {
            NavHost(
                navController = navController,
                startDestination = viewState.initialRoute,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {

                animatedComposable(Routes.GameRoute().route) { GameScreen() }

                animatedComposable(Routes.SettingsRoute.MainRoute().route) { SettingsScreen() }
                animatedComposable(
                    Routes.SettingsRoute.ThermsRoute.route,
                    animationType = AnimationType.FADE
                ) { TermsScreen() }
                animatedComposable(
                    Routes.SettingsRoute.LanguageRoute.route,
                    animationType = AnimationType.FADE
                ) { LanguageScreen() }
                animatedComposable(
                    Routes.SettingsRoute.AboutRoute.route,
                    animationType = AnimationType.FADE
                ) { AboutScreen() }
                animatedComposable(
                    Routes.SettingsRoute.DonateRoute.route,
                    animationType = AnimationType.FADE
                ) { DonateScreen() }

            }

        }

        when (viewAction) {
            null -> {}
        }
    }
}

