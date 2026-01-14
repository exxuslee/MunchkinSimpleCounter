package com.exxuslee.munchkinsimplecounter.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.exxuslee.munchkinsimplecounter.features.fight.FightScreen
import com.exxuslee.munchkinsimplecounter.features.fight.FightViewModel
import com.exxuslee.munchkinsimplecounter.features.game.GameScreen
import com.exxuslee.munchkinsimplecounter.features.game.GameViewModel
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState
import com.exxuslee.munchkinsimplecounter.features.settings.about.AboutScreen
import com.exxuslee.munchkinsimplecounter.features.settings.donate.DonateScreen
import com.exxuslee.munchkinsimplecounter.features.settings.language.LanguageScreen
import com.exxuslee.munchkinsimplecounter.features.settings.main.SettingsScreen
import com.exxuslee.munchkinsimplecounter.features.settings.terms.TermsScreen
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.ui.common.AnimationType
import com.exxuslee.munchkinsimplecounter.ui.common.animatedComposable


@Composable
fun NavHostContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewState: ViewState,
    gameViewModel: GameViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = viewState.initialRoute,
        modifier = modifier
    ) {
        animatedComposable(Routes.GameRoute.route) { GameScreen(viewModel = gameViewModel) }

        animatedComposable(
            Routes.FightRoute.route,
            animationType = AnimationType.FADE
        ) { FightScreen() }

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