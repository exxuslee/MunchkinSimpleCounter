package com.exxuslee.munchkinsimplecounter.features.game

import com.exxuslee.munchkinsimplecounter.features.game.models.Action
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class GameViewModel(

) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {


    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            else -> {}
        }

    }

}