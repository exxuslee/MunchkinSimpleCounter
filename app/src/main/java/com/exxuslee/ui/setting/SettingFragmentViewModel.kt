package com.exxuslee.ui.setting

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
    private var selectedID = 0

    private val _players = MutableLiveData<List<Player>?>()
    val players = _players.asLiveData()

    private var handleResult = object : HandleResult<List<Player>> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: List<Player>) {
            _players.postValue(data)
        }
    }

    fun savePlayer(player: Player) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.savePlayer(player) }
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
        }

    }

    private fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

    companion object {
        const val TAG = "Munchkin simple"
    }
}

