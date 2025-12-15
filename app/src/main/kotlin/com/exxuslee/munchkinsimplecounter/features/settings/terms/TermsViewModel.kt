package com.exxuslee.munchkinsimplecounter.features.settings.terms

import com.exxuslee.domain.usecases.SettingsUseCase
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import com.exxuslee.munchkinsimplecounter.features.settings.terms.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.terms.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.terms.models.ViewState

class TermsViewModel(
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState(
        isTermsOfUseRead = settingsUseCase.isTermsOfUseRead()
    )
) {

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.ReadTerms -> {
                settingsUseCase.isTermsOfUseRead(true)
                viewState = viewState.copy(isTermsOfUseRead = true)
            }
        }

    }

}