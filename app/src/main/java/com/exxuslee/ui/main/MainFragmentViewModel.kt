package com.exxuslee.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exxuslee.BuildConfig
import com.exxuslee.core.ProvideInstance
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.UseCaseCache
import com.exxuslee.domain.usecases.UseCaseDB
import com.exxuslee.domain.utils.HandleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainFragmentViewModel(
    private val playerUseCase: UseCaseDB.Base,
    private val cacheUseCase: UseCaseCache.Base
) : ViewModel() {
    val provideInstance = ProvideInstance.Base(BuildConfig.DEBUG)
    private var selectedID = -1

    private val _players = MutableLiveData<List<Player>?>()
    val players = _players as LiveData<List<Player>>

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            _players.postValue(null)
            _players.postValue(data.filter { player -> player.playing })
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
        }
    }

    //private fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

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
        if (newPlayers != null) for (player in newPlayers) updatePlayer(player)
        _players.postValue(newPlayers)
        Log.d(TAG, _players.value.toString())
    }

    fun changeIcon(position: Int) {
        updatePlayer(
            Player(
                id = _players.value?.get(position)!!.id,
                name = _players.value?.get(position)!!.name,
                level = _players.value?.get(position)!!.level,
                bonus = _players.value?.get(position)!!.bonus,
                icon = _players.value?.get(position)!!.icon,
                playing = true,
                reverseSex = !_players.value?.get(position)!!.reverseSex
            )
        )
    }

    fun darkMode(darkMode: Boolean) {
        val mode = cacheUseCase.loadBoolean("DARK_STATE")
        cacheUseCase.saveBoolean("DARK_STATE", !mode)
        Log.d(TAG, mode.toString())

//        if (darkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
    }

    companion object {
        const val TAG = "player"
    }
}

