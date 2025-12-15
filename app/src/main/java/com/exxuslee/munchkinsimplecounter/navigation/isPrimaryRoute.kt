package com.exxuslee.munchkinsimplecounter.navigation

fun Routes.isPrimaryRoute(): Boolean {
    return when (this) {
        is Routes.SettingsRoute.MainRoute,
        is Routes.GameRoute,
            -> true

        else -> false
    }
}