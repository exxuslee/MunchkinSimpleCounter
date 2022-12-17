package com.exxuslee.ui.setting

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

class SettingFragmentViewModel(private val playerUseCase: UseCase.Base) : ViewModel() {
    private val _players = MutableLiveData<List<Player>?>()
    val players = _players.asLiveData()

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            _players.postValue(null)
            _players.postValue(data)
        }
    }

    fun savePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val lastId = playerUseCase.savePlayer(player)
                Log.d(TAG, lastId.toString())
            }
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
            loadPlayers()
        }

    }

    private fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

    fun onlinePlayer(position: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playerUseCase.updatePlayer(
                    Player(
                        id = _players.value?.get(position)!!.id,
                        name = _players.value?.get(position)!!.name,
                        level = _players.value?.get(position)!!.level,
                        bonus = _players.value?.get(position)!!.bonus,
                        icon = _players.value?.get(position)!!.icon,
                        playing = !_players.value?.get(position)!!.playing,
                        reverseSex = _players.value?.get(position)!!.reverseSex
                    )
                )
            }
            loadPlayers()
        }
    }

    companion object {
        const val TAG = "player"
    }
}

