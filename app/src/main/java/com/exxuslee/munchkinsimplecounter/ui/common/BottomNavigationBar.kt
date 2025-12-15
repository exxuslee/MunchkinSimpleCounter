package com.exxuslee.munchkinsimplecounter.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.navigation.isParentSelected
import com.exxuslee.munchkinsimplecounter.ui.root.models.Event
import com.exxuslee.munchkinsimplecounter.ui.root.models.ViewState

@Composable
fun BottomNavigationBar(
    viewState: ViewState,
    backStackEntry: NavBackStackEntry?,
    navController: NavHostController,
    eventHandler: (Event) -> Unit,
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
    ) {
        listOf(
            Routes.GameRoute(),
            Routes.GameRoute(),
            Routes.SettingsRoute.MainRoute(),
        ).forEach { dest ->
            val currentRoute = backStackEntry?.destination?.route
            NavigationBarItem(
                selected = dest.isParentSelected(currentRoute),
                onClick = {
                    if (dest.isParentSelected(currentRoute)) {
                        navController.popBackStack(dest.route, inclusive = false)
                    } else {
                        eventHandler.invoke(Event.MainRoute(dest.route))
                        navController.navigate(dest.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = dest.icon(),
                        contentDescription = dest.label(),
                        modifier = Modifier.size(30.dp)
                    )
                },
                alwaysShowLabel = false,
            )
        }
    }
}