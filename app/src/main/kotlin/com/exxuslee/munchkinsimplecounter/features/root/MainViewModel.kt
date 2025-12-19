package com.exxuslee.munchkinsimplecounter.features.root

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.exxuslee.munchkinsimplecounter.features.root.models.Action
import com.exxuslee.munchkinsimplecounter.features.root.models.MainEvent
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.managers.PuzzleWorkManager
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    val workManager: PuzzleWorkManager,
) : BaseViewModel<ViewState, Action, MainEvent>(
    initialState = ViewState(
        initialRoute = Routes.GameRoute.route,
    )
) {

    init {
        viewModelScope.launch(Dispatchers.Default) {
            workManager.startWork()
            workManager.observeProgress().collect {
                if (it == "done") {
                    Log.d("PuzzleWorker", "Puzzle $it")
                    workManager.startWork()
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: MainEvent) {
        when (viewEvent) {
            is MainEvent.MainRoute -> true

        }

    }

}