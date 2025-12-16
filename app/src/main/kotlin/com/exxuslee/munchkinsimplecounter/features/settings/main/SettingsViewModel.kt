package com.exxuslee.munchkinsimplecounter.features.settings.main

import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.PlayersUseCase
import com.exxuslee.domain.usecases.SettingsUseCase
import com.exxuslee.domain.usecases.ThemeController
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Action
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.Event
import com.exxuslee.munchkinsimplecounter.features.settings.main.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val themeController: ThemeController,
    private val settingsUseCase: SettingsUseCase,
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                themeController.isDark,
                settingsUseCase.isTermsOfUseRead,
                playersUseCase.players,
            ) { isDark, isTermsOfUseRead, players ->
                ViewState(
                    isTermsOfUseRead = isTermsOfUseRead,
                    isDark = isDark,
                    players = players
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

            Event.DialogNewGame -> viewAction = Action.NewGame

            Event.ConfirmNewGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.players.value.forEach { player ->
                        val resetPlayer = player.copy(
                            level = 1,
                            bonus = 0,
                            reverseSex = false,
                        )
                        playersUseCase.updatePlayer(resetPlayer)
                    }
                    delay(500)
                    viewAction = Action.PopBack
                }
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer

            is Event.AddPlayer -> {
                viewModelScope.launch {
                    playersUseCase.savePlayer(Player(name = viewEvent.name, icon = viewEvent.icon))
                }
                clearAction()

            }

            is Event.ActivatePlayer -> viewModelScope.launch {
                playersUseCase.updatePlayer(viewEvent.player.copy(playing = !viewEvent.player.playing))
            }

        }

    }

}