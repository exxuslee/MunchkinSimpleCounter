package com.exxuslee.munchkinsimplecounter.features.root

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Scaffold
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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.game.GameViewModel
import com.exxuslee.munchkinsimplecounter.features.game.models.BottomButtonsItems
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.GameViewState
import com.exxuslee.munchkinsimplecounter.features.root.models.Action
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState
import com.exxuslee.munchkinsimplecounter.managers.ClickSound
import com.exxuslee.munchkinsimplecounter.managers.rememberSoundManager
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.navigation.asRoute
import com.exxuslee.munchkinsimplecounter.navigation.isPrimaryRoute
import com.exxuslee.munchkinsimplecounter.ui.common.DiceRollDialog
import com.exxuslee.munchkinsimplecounter.ui.common.LocalNavController
import com.exxuslee.munchkinsimplecounter.ui.common.VSpacer
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MainContent(
    mainViewModel: MainViewModel = koinViewModel(),
    gameViewModel: GameViewModel = koinViewModel(),
) {
    val mainViewState by mainViewModel.viewStates().collectAsState()
    val gameViewState by gameViewModel.viewStates().collectAsState()
    val viewAction by mainViewModel.viewActions().collectAsState(null)

    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.asRoute()
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        if (isLandscape) {
            LandscapeLayout(
                currentRoute,
                navController,
                gameViewModel,
                mainViewModel,
                mainViewState,
                gameViewState
            )
        } else {
            PortraitLayout(currentRoute, navController, mainViewModel, mainViewState, gameViewModel)
        }

        when (viewAction) {
            Action.Dice -> DiceRollDialog { mainViewModel.clearAction() }

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
    gameViewModel: GameViewModel,
) {
    Scaffold(
        topBar = { MainTopBar(currentRoute, navController, viewModel) },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        NavHostContent(
            navController,
            viewState,
            gameViewModel,
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
    gameViewModel: GameViewModel,
    mainViewModel: MainViewModel,
    viewState: ViewState,
    gameViewState: GameViewState,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            NavigationRail(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                windowInsets = WindowInsets(),
            ) {
                LandscapeNavigationButtons(
                    currentRoute,
                    navController,
                    gameViewModel,
                    mainViewModel,
                    gameViewState
                )
            }
            NavHostContent(
                navController,
                viewState,
                gameViewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            )
        }
    }
}


@Composable
private fun LandscapeNavigationButtons(
    currentRoute: Routes?,
    navController: NavHostController,
    gameViewModel: GameViewModel,
    mainViewModel: MainViewModel,
    gameViewState: GameViewState,
) {
    val soundManager = rememberSoundManager()
    if (currentRoute?.isPrimaryRoute() == false) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                contentDescription = stringResource(R.string.back)
            )
        }
    } else {
        IconButton(onClick = {
            navController.navigate(Routes.SettingsRoute.MainRoute.route)
        }) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_settings_24),
                contentDescription = stringResource(R.string.title_settings)
            )
        }
    }
    VSpacer(16.dp)
    if (gameViewState.activePlayers.isNotEmpty() && currentRoute?.isPrimaryRoute() == true) listOf(
        BottomButtonsItems.AddLevel,
        BottomButtonsItems.SubLevel,
        BottomButtonsItems.AddBonus,
        BottomButtonsItems.SubBonus,
    ).forEach { dest ->

        IconButton(onClick = {
            if (gameViewState.isSound) soundManager.play(
                when (dest) {
                    BottomButtonsItems.AddLevel -> ClickSound.ADD_LEVEL
                    BottomButtonsItems.SubLevel -> ClickSound.SUB_LEVEL
                    BottomButtonsItems.AddBonus -> ClickSound.ADD_BONUS
                    BottomButtonsItems.SubBonus -> ClickSound.SUB_BONUS
                }
            )

            gameViewModel.obtainEvent(
                when (dest) {
                    BottomButtonsItems.AddLevel -> Event.AddLevel
                    BottomButtonsItems.SubLevel -> Event.SubLevel
                    BottomButtonsItems.AddBonus -> Event.AddBonus
                    BottomButtonsItems.SubBonus -> Event.SubBonus
                }
            )
        }) {
            Icon(
                painter = dest.icon(),
                contentDescription = dest.label(),
            )
        }
        VSpacer(4.dp)

    }

}

