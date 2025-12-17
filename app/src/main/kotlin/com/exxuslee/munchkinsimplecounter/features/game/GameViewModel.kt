package com.exxuslee.munchkinsimplecounter.features.game
import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.model.UiState
import com.exxuslee.domain.usecases.PlayersUseCase
import com.exxuslee.munchkinsimplecounter.R
import com.exxuslee.munchkinsimplecounter.features.game.models.Action
import com.exxuslee.munchkinsimplecounter.features.game.models.Event
import com.exxuslee.munchkinsimplecounter.features.game.models.GameViewState
import com.exxuslee.munchkinsimplecounter.ui.common.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(
    private val playersUseCase: PlayersUseCase,
) : BaseViewModel<GameViewState, Action, Event>(
    initialState = GameViewState()
) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            playersUseCase.players.collect {
                val activePlayers = it.filter { player -> player.playing }
                viewState = viewState.copy(
                    activePlayers = activePlayers,
                    allPlayers = it.size,
                    state = UiState.Idle,
                )
            }
        }
    }

    override fun obtainEvent(viewEvent: Event) {
        when (viewEvent) {

            Event.AddBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activePlayers.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(bonus = player.bonus + 1))
                }
            }

            Event.AddLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activePlayers.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(level = player.level + 1))
                }
            }

            Event.SubBonus -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activePlayers.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
                    val player = playersUseCase.player(id) ?: return@launch
                    playersUseCase.updatePlayer(player.copy(bonus = player.bonus - 1))
                }
            }

            Event.SubLevel -> viewModelScope.launch(Dispatchers.IO) {
                val id = viewState.selectedPlayerId
                if (id == null || !viewState.activePlayers.map { it.id }.contains(id)) {
                    viewAction = Action.ShowSelectPlayerMessage(R.string.select_player_toast_message)
                } else {
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

            is Event.AddPlayer ->  {
                viewModelScope.launch(Dispatchers.IO) {
                    playersUseCase.savePlayer(Player(name = viewEvent.name, icon = viewEvent.icon))
                }
                viewAction = Action.ShowSelectPlayerMessage(R.string.add_player_toast_message)
            }

            Event.DialogAddPlayer -> viewAction = Action.AddPlayer
        }

    }

}