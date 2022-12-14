package com.exxuslee.ui.main

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
    val players = _players.asLiveData()

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

    private fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>


    fun selectedPlayer() = selectedID

    fun selectPlayer(position: Int) {
        selectedID = position
    }

    fun level(i: Int) {
        if (selectedID >= 0) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    playerUseCase.updatePlayer(
                        Player(
                            name = _players.value?.get(selectedID)!!.name,
                            level = _players.value?.get(selectedID)!!.level + i,
                            bonus = _players.value?.get(selectedID)!!.bonus,
                            sex = _players.value?.get(selectedID)!!.sex,
                            playing = true,
                            reverseSex = _players.value?.get(selectedID)!!.reverseSex
                        )
                    )
                }
                loadPlayers()
            }
        }
    }

    fun bonus(i: Int) {
        if (selectedID >= 0) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    playerUseCase.updatePlayer(
                        Player(
                            name = _players.value?.get(selectedID)!!.name,
                            level = _players.value?.get(selectedID)!!.level,
                            bonus = _players.value?.get(selectedID)!!.bonus + i,
                            sex = _players.value?.get(selectedID)!!.sex,
                            playing = true,
                            reverseSex = _players.value?.get(selectedID)!!.reverseSex
                        )
                    )
                }
                loadPlayers()
            }
        }
    }

    companion object {
        const val TAG = "Munchkin simple"
    }
}

