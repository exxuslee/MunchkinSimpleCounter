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
    private val playerMapper: PlayerMapper.Base = PlayerMapper.Base()

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            communication.post(data.filter { Player -> Player.playing })
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
            playerMapper.map(
                communication.value()[selectedID],
                communication.value()[selectedID].level + i,
                null,
                null
            )
        )
    }

    fun bonus(i: Int) {
        if (selectedID >= 0) updatePlayer(
            playerMapper.map(
                communication.value()[selectedID],
                null,
                communication.value()[selectedID].bonus + i,
                null
            )
        )
    }

    fun newGame() {
        val newPlayers = communication.value().map { player ->
            playerMapper.map(player, 1, 0, false)
        }
        for (player in newPlayers) updatePlayer(player)
        communication.put(newPlayers)
    }

    fun changeIcon(position: Int) {
        updatePlayer(
            playerMapper.map(
                communication.value()[selectedID],
                null,
                null,
                !communication.value()[position].reverseSex
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

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            Log.d(TAG, "isFirstRun ")
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Player>>) =
        communication.observe(owner, observer)

    companion object {
        const val TAG = "player"
    }
}

