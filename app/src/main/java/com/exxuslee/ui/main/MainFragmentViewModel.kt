package com.exxuslee.ui.main

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exxuslee.core.Communication
import com.exxuslee.core.Init
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.UseCaseCache
import com.exxuslee.domain.usecases.UseCaseDB
import com.exxuslee.domain.utils.HandleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragmentViewModel(
    private val playerUseCase: UseCaseDB.Base,
    private val cacheUseCase: UseCaseCache.Base,
    private val communication: MainCommunication.Mutable,
) : ViewModel(), Init, Communication.Observe<List<Player>> {
    private var selectedID = -1

    private var _players = mutableListOf<Player>()

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            _players = (data.filter { Player -> Player.playing }) as MutableList<Player>
            communication.post(data)
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
        }
    }

    fun selectPlayer(playerId: Int) {
        selectedID = playerId
    }

    private fun updatePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playerUseCase.updatePlayer(player)
            }
            loadPlayers()
        }
    }

    fun level(i: Int) {
        if (selectedID >= 0) updatePlayer(
            Player(
                id = _players[selectedID].id,
                name = _players[selectedID].name,
                level = _players[selectedID].level + i,
                bonus = _players[selectedID].bonus,
                icon = _players[selectedID].icon,
                playing = true,
                reverseSex = _players[selectedID].reverseSex
            )
        )
    }

    fun bonus(i: Int) {
        if (selectedID >= 0) updatePlayer(
            Player(
                id = _players.get(selectedID).id,
                name = _players.get(selectedID).name,
                level = _players.get(selectedID).level,
                bonus = _players.get(selectedID).bonus + i,
                icon = _players.get(selectedID).icon,
                playing = true,
                reverseSex = _players.get(selectedID)!!.reverseSex
            )
        )
    }

    fun newGame() {
        val newPlayers = _players.map { player ->
            Player(
                id = player.id,
                name = player.name,
                level = 1,
                bonus = 0,
                icon = player.icon,
                playing = player.playing,
                reverseSex = false
            )
        }
        for (player in newPlayers) updatePlayer(player)
        communication.put(newPlayers)
    }

    fun changeIcon(position: Int) {
        updatePlayer(
            Player(
                id = _players[position].id,
                name = _players[position].name,
                level = _players[position].level,
                bonus = _players[position].bonus,
                icon = _players[position].icon,
                playing = true,
                reverseSex = !_players[position].reverseSex
            )
        )
    }

    fun loadMode() {
        val mode = cacheUseCase.loadBoolean("DARK_STATE")
        if (mode) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun saveMode() {
        val mode = cacheUseCase.loadBoolean("DARK_STATE")
        cacheUseCase.saveBoolean("DARK_STATE", !mode)
    }

    companion object {
        const val TAG = "player"
    }

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            Log.d(TAG, "isFirstRun ")
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Player>>) =
        communication.observe(owner, observer)
}

