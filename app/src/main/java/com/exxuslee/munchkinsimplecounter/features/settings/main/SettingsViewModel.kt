package com.exxuslee.munchkinsimplecounter.features.settings.main

import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.usecases.SettingsUseCase
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeController: ThemeController,
    private val settingsUseCase: SettingsUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {
        viewModelScope.launch {
            combine(
                themeController.isDark,
                settingsUseCase.isTermsOfUseRead,
            ) { isDark, isTermsOfUseRead ->
                ViewState(
                    isTermsOfUseRead = isTermsOfUseRead,
                    isDark = isDark,
                )
            }.collect { newState ->
                viewState = newState
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            is Event.IsDark -> {
                themeController.setDark(viewEvent.newValue)
                viewState = viewState.copy(isDark = viewEvent.newValue)
            }

        }

    }

}