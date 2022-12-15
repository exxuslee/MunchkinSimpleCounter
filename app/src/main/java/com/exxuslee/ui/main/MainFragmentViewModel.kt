package com.exxuslee.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.UseCase
import com.exxuslee.domain.utils.HandleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragmentViewModel(private val playerUseCase: UseCase.Base) : ViewModel() {
    private var selectedID = -1

    private val _players = MutableLiveData<List<Player>?>()
    val players = _players as LiveData<List<Player>>

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            _players.postValue(null)
            _players.postValue(data)
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
        }
    }

    //private fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

    fun selectPlayer(position: Int) {
        selectedID = position
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
                id = _players.value?.get(selectedID)!!.id,
                name = _players.value?.get(selectedID)!!.name,
                level = _players.value?.get(selectedID)!!.level + i,
                bonus = _players.value?.get(selectedID)!!.bonus,
                icon = _players.value?.get(selectedID)!!.icon,
                playing = true,
                reverseSex = _players.value?.get(selectedID)!!.reverseSex
            )
        )
    }

    fun bonus(i: Int) {
        if (selectedID >= 0) updatePlayer(
            Player(
                id = _players.value?.get(selectedID)!!.id,
                name = _players.value?.get(selectedID)!!.name,
                level = _players.value?.get(selectedID)!!.level,
                bonus = _players.value?.get(selectedID)!!.bonus + i,
                icon = _players.value?.get(selectedID)!!.icon,
                playing = true,
                reverseSex = _players.value?.get(selectedID)!!.reverseSex
            )
        )
    }

    fun newGame() {
        val newPlayers = _players.value?.map { player ->
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
        _players.postValue(newPlayers)
        Log.d(TAG, _players.value.toString())
    }

    companion object {
        const val TAG = "player"
    }
}

