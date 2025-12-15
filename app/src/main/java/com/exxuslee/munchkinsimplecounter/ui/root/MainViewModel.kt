package com.exxuslee.munchkinsimplecounter.ui.root

import com.exxuslee.munchkinsimplecounter.navigation.Routes
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import com.exxuslee.munchkinsimplecounter.ui.root.models.Action
import com.exxuslee.munchkinsimplecounter.ui.root.models.Event
import com.exxuslee.munchkinsimplecounter.ui.root.models.ViewState

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