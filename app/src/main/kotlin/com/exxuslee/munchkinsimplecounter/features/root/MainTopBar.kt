package com.exxuslee.munchkinsimplecounter.features.root

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.root.models.MainEvent
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.navigation.isPrimaryRoute
import com.exxuslee.munchkinsimplecounter.ui.common.HSpacer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    currentRoute: Routes?,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    ) {
    TopAppBar(
        title = {
            Text(
                text = currentRoute?.label() ?: "",
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
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
                    navController.navigate(Routes.FightRoute.route)
                }) {
                    Icon(
                        painter = Routes.FightRoute.icon(),
                        contentDescription = Routes.FightRoute.label()
                    )
                }
                IconButton(onClick = {
                    mainViewModel.obtainEvent(MainEvent.Dice)
                }) {
                    Icon(
                        painterResource(id = R.drawable.outline_dice_5_24),
                        contentDescription = stringResource(R.string.dice)
                    )
                }
                IconButton(onClick = {
                    navController.navigate(Routes.SettingsRoute.MainRoute.route)
                }) {
                    Icon(
                        painter = Routes.SettingsRoute.MainRoute.icon(),
                        contentDescription = Routes.SettingsRoute.MainRoute.label()
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    )
}