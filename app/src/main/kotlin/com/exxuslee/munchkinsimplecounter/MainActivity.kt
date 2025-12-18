package com.exxuslee.munchkinsimplecounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.munchkinsimplecounter.features.root.MainContent
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

        val workManager = WorkManager.getInstance(this)
        val existingWork = workManager.getWorkInfosByTag("PUZZLE").get()
        if (existingWork.none { !it.state.isFinished }) {
            startWorker()
        }

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

    private fun startWorker() {
        val request = OneTimeWorkRequestBuilder<PuzzleWorker>().addTag("PUZZLE").build()
        WorkManager.getInstance(this).enqueueUniqueWork(
            "puzzle_work",
            ExistingWorkPolicy.KEEP,
            request
        )
        observeProgress()
    }

    private fun observeProgress() {
        lifecycleScope.launch {
            WorkManager.getInstance(this@MainActivity)
                .getWorkInfosForUniqueWorkFlow("puzzle_work")
                .collectLatest { workInfoList ->
                    workInfoList.firstOrNull()?.let { info ->
                        val progress = info.progress.getInt("progress", 0)
                        val total = info.progress.getInt("total", 1)
                        println("Progress: $progress / $total")
                    }
                }
        }
    }

}
