package com.exxuslee.munchkinsimplecounter.features.root

import com.exxuslee.munchkinsimplecounter.features.root.models.Action
import com.exxuslee.munchkinsimplecounter.features.root.models.MainEvent
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState
import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class MainViewModel() : BaseViewModel<ViewState, Action, MainEvent>(
    initialState = ViewState(
        initialRoute = Routes.GameRoute.route,
    )
) {

    override fun obtainEvent(viewEvent: MainEvent) {
        when (viewEvent) {
            is MainEvent.MainRoute -> true

        }

    }

}