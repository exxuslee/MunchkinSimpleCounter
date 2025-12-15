package com.exxuslee.munchkinsimplecounter.features.settings.players

import com.exxuslee.munchkinsimplecounter.features.settings.players.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.players.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.players.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class PlayersViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {


    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {


            else -> {}
        }

    }

    suspend fun sync() {

    }
}