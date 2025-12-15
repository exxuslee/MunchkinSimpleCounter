package com.exxuslee.munchkinsimplecounter.ui.settings.language

import com.exxuslee.munchkinsimplecounter.ui.settings.language.models.Action
import com.exxuslee.munchkinsimplecounter.ui.settings.language.models.Event
import com.exxuslee.munchkinsimplecounter.ui.settings.language.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel

class LanguageViewModel(
) : BaseViewModel<ViewState, Action, Event>(initialState = ViewState()) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.Select -> viewAction = Action.SetLocale(viewEvent.type)
        }

    }

}