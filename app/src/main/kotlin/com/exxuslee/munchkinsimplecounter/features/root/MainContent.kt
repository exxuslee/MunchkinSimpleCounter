package com.exxuslee.munchkinsimplecounter.features.root

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.game.GameScreen
import com.exxuslee.munchkinsimplecounter.features.root.models.Event
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState
import com.exxuslee.munchkinsimplecounter.features.settings.about.AboutScreen
import com.exxuslee.munchkinsimplecounter.features.settings.donate.DonateScreen
import com.exxuslee.munchkinsimplecounter.features.settings.language.LanguageScreen
import com.exxuslee.munchkinsimplecounter.features.settings.main.SettingsScreen
import com.exxuslee.munchkinsimplecounter.features.settings.terms.TermsScreen
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.navigation.asRoute
import com.exxuslee.munchkinsimplecounter.navigation.isPrimaryRoute
import com.exxuslee.munchkinsimplecounter.ui.common.AnimationType
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer
import com.exxuslee.munchkinsimplecounter.ui.common.LocalNavController
import com.exxuslee.munchkinsimplecounter.ui.common.animatedComposable
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainContent(
    viewModel: MainViewModel = koinViewModel(),
) {
    val viewState by viewModel.viewStates().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.asRoute()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        if (isLandscape) {
            LandscapeLayout(currentRoute, navController, viewModel, viewState)
        } else {
            PortraitLayout(currentRoute, navController, viewModel, viewState)
        }

        when (viewAction) {
            null -> {}
        }
    }
}

@Composable
private fun PortraitLayout(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel,
    viewState: ViewState,
) {
    Scaffold(
        topBar = { MainTopBar(currentRoute, navController, viewModel) },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        NavHostContent(
            navController,
            viewState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        )
    }
}

@Composable
private fun LandscapeLayout(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel,
    viewState: ViewState,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavigationRail(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)) {
                LandscapeNavigationButtons(currentRoute, navController, viewModel)
            }
            NavHostContent(
                navController, viewState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainTopBar(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    TopAppBar(
        title = {
            Text(
                text = currentRoute?.label() ?: "",
                style = MaterialTheme.typography.headlineMedium
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
            } else HSpacer(48.dp)
        },
        actions = {
            if (currentRoute?.isPrimaryRoute() == true) {
                IconButton(onClick = {
                    viewModel.obtainEvent(Event.MainRoute(Routes.SettingsRoute.MainRoute.route))
                    navController.navigate(Routes.SettingsRoute.MainRoute.route)
                }) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_settings_24),
                        contentDescription = stringResource(R.string.title_settings)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    )
}

@Composable
private fun LandscapeNavigationButtons(
    currentRoute: Routes?,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    IconButton(onClick = { navController.popBackStack() }) {
        Icon(
            painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
            contentDescription = stringResource(R.string.back)
        )
    }
    HSpacer(16.dp)
    IconButton(onClick = {
        viewModel.obtainEvent(Event.MainRoute(Routes.SettingsRoute.MainRoute.route))
        navController.navigate(Routes.SettingsRoute.MainRoute.route)
    }) {
        Icon(
            painterResource(id = R.drawable.ic_baseline_settings_24),
            contentDescription = stringResource(R.string.title_settings)
        )
    }
}

@Composable
private fun NavHostContent(
    navController: NavHostController,
    viewState: ViewState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = viewState.initialRoute,
        modifier = modifier
    ) {
        animatedComposable(Routes.GameRoute.route) { GameScreen() }
        animatedComposable(Routes.SettingsRoute.MainRoute.route) { SettingsScreen() }
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