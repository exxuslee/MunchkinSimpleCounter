package com.exxuslee.munchkinsimplecounter.navigation

import androidx.navigation.NavDestination

fun NavDestination?.asRoute(): Routes? {
    val route = this?.route ?: return null
    return when (route) {
        // Game
        Routes.GameRoute.route -> Routes.GameRoute

        Routes.FightRoute.route -> Routes.FightRoute

        // Settings
        Routes.SettingsRoute.MainRoute.route -> Routes.SettingsRoute.MainRoute
        Routes.SettingsRoute.ThermsRoute.route -> Routes.SettingsRoute.ThermsRoute
        Routes.SettingsRoute.LanguageRoute.route -> Routes.SettingsRoute.LanguageRoute
        Routes.SettingsRoute.DonateRoute.route -> Routes.SettingsRoute.DonateRoute
        Routes.SettingsRoute.AboutRoute.route -> Routes.SettingsRoute.AboutRoute
        else -> null
    }
}