package com.exxuslee.ui.setting

import android.util.Log
import androidx.lifecycle.*
import com.exxuslee.core.Communication
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.UseCaseDB
import com.exxuslee.domain.utils.HandleResult
import com.exxuslee.ui.main.MainCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragmentViewModel(
    private val playerUseCase: UseCaseDB.Base,
    private val communication: MainCommunication.Mutable
) : ViewModel(), Communication.Observe<List<Player>> {
    //    private val _players = MutableLiveData<List<Player>?>()
    //    val players = _players.asLiveData()
    var players = mutableListOf<Player>()

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
//            _players.postValue(null)
//            _players.postValue(data)
            players = (data.filter { Player -> Player.playing }) as MutableList<Player>
            communication.post(data)
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
                        id = players[position].id,
                        name = players[position].name,
                        level = players[position].level,
                        bonus = players[position].bonus,
                        icon = players[position].icon,
                        playing = !players[position].playing,
                        reverseSex = players[position].reverseSex
                    )
                )
            }
        }
        loadPlayers()
    }

    fun deletePlayer(selectedItemPosition: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                players[selectedItemPosition].let { playerUseCase.deletePlayer(it.id) }
            }
        }
    }

    companion object {
        const val TAG = "player"
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Player>>) =
        communication.observe(owner, observer)
}

