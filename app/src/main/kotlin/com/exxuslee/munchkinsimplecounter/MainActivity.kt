package com.exxuslee.munchkinsimplecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.munchkinsimplecounter.features.root.MainContent
import com.exxuslee.munchkinsimplecounter.puzzle.PuzzleWorkManager
import com.exxuslee.munchkinsimplecounter.ui.common.rememberDoubleBackPressHandler
import com.exxuslee.munchkinsimplecounter.ui.theme.AppTheme
import com.exxuslee.puzzle.PuzzleWorker
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.util.UUID

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themeController: ThemeController = koinInject()
            val puzzleWorkManager: PuzzleWorkManager = koinInject()
            val isDark by themeController.isDark.collectAsState()
            val doubleBackPressHandler = rememberDoubleBackPressHandler(this@MainActivity)

            LaunchedEffect(Unit) {
                puzzleWorkManager.startWork()
            }

            BackHandler {
                doubleBackPressHandler.handleBackPress()
            }

            AppTheme(isDark) {
                MainContent()
            }
        }
    }

}
