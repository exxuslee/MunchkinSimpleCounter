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
    val communication: MainCommunication.Mutable
) : ViewModel(), Communication.Observe<List<Player>> {

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
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
                        id = communication.value()[position].id,
                        name = communication.value()[position].name,
                        level = communication.value()[position].level,
                        bonus = communication.value()[position].bonus,
                        icon = communication.value()[position].icon,
                        playing = !communication.value()[position].playing,
                        reverseSex = communication.value()[position].reverseSex
                    )
                )
            }
        }
        loadPlayers()
    }

    fun deletePlayer(selectedItemPosition: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                communication.value()[selectedItemPosition].let { playerUseCase.deletePlayer(it.id) }
            }
        }
    }

    companion object {
        const val TAG = "player"
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<List<Player>>) =
        communication.observe(owner, observer)
}

