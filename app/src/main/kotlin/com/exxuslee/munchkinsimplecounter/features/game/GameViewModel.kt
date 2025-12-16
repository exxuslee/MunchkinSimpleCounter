package com.exxuslee.munchkinsimplecounter.features.game

import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.usecases.PlayersUseCase
import com.exxuslee.munchkinsimplecounter.features.game.models.Action
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.ViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<ViewState, Action, Event>(
    initialState = ViewState()
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playersUseCase.players.collect {
                viewState = viewState.copy(players = it)
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {
            Event.AddBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null) viewAction = Action.ShowSelectPlayerMessage
                else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(bonus = player.bonus + 1))
                }
            }

            Event.AddLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null) viewAction = Action.ShowSelectPlayerMessage
                else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(level = player.level + 1))
                }
            }

            Event.SubBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null) viewAction = Action.ShowSelectPlayerMessage
                else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(bonus = player.bonus - 1))
                }
            }

            Event.SubLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null) viewAction = Action.ShowSelectPlayerMessage
                else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(level = player.level - 1))
                }
            }

            is Event.SelectPlayer -> {
                viewState = viewState.copy(selectedPlayerId = viewEvent.id)
            }

            is Event.SwitchSex -> {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.updatePlayer(
                        viewEvent.player.copy(
                            reverseSex = !viewEvent.player.reverseSex
                        )
                    )
                }
            }

        }

    }

}