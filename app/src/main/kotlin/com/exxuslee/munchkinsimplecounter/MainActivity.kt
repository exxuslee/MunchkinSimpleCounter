package com.exxuslee.munchkinsimplecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.munchkinsimplecounter.ui.common.rememberDoubleBackPressHandler
import com.exxuslee.munchkinsimplecounter.features.root.MainContent
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeController: ThemeController = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val doubleBackPressHandler = rememberDoubleBackPressHandler(this@MainActivity)

            BackHandler {
                doubleBackPressHandler.handleBackPress()
            }

            AppTheme(isDark) {
                MainContent()
            }

        }
    }

}
