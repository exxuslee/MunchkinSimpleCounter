package com.exxuslee.ui

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
    private var selectedID = 0

    private val _players = MutableLiveData<List<Player>?>()
    val players = _players.asLiveData()

    private var handleResult = object : HandleResult<Player> {
        override fun handleError(message: String) {
        }

        override fun handleSuccess(data: Player) {
            _players.value = _players.value?.plus(data)
        }
    }

    fun loadPlayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { playerUseCase.players().handle(handleResult) }
        }
    }

    companion object {
        const val TAG = "Munchkin simple"
    }
}

/**
 * This functions helps in transforming a [MutableLiveData] of type [T]
 * to a [LiveData] of type [T]
 */
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>