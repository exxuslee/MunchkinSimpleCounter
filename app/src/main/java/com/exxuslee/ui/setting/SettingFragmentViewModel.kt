package com.exxuslee.ui.setting

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exxuslee.core.Communication
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.usecases.UseCaseDB
import com.exxuslee.domain.utils.HandleResult
import com.exxuslee.ui.PlayerMapper
import com.exxuslee.ui.main.MainCommunication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingFragmentViewModel(
    private val playerUseCase: UseCaseDB.Base,
    val communication: MainCommunication.Mutable,
    private val playerMapper: PlayerMapper.Base
) : ViewModel(), Setting, Communication.Observe<List<Player>> {

    companion object {
        const val TAG = "player"
    }

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            communication.post(data)
        }
    }

    override fun savePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val lastId = playerUseCase.savePlayer(player)
                Log.d(TAG, lastId.toString())
            }
        }
    }

    override fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
            loadPlayers()
        }

    }

    override fun onlinePlayer(position: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                playerUseCase.updatePlayer(
                    playerMapper.map(
                        communication.value()[position],
                        null,
                        null,
                        null,
                        !communication.value()[position].playing
                    )
                )
            }
        }
        loadPlayers()
    }

    override fun deletePlayer(selectedItemPosition: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                communication.value()[selectedItemPosition].let { playerUseCase.deletePlayer(it.id) }
            }
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Player>>) =
        communication.observe(owner, observer)
}

