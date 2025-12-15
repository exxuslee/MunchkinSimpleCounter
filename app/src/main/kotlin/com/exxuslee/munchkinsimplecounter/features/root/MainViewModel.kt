package com.exxuslee.munchkinsimplecounter.features.root

import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import com.exxuslee.munchkinsimplecounter.features.root.models.Action
import com.exxuslee.munchkinsimplecounter.features.root.models.Event
import com.exxuslee.munchkinsimplecounter.features.root.models.ViewState

class MainViewModel(

) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        initialRoute = Routes.GameRoute.route,
    )
) {

    init {

    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.MainRoute -> true

        }

    }

}