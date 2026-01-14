package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.PlayersRepository
import kotlinx.coroutines.flow.StateFlow

interface PlayersUseCase {

    val players: StateFlow<List<Player>>
    val activePlayers: StateFlow<List<Player>>
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player)
    suspend fun deletePlayer(id: Int)
    suspend fun player(id: Int): Player?
    fun selectId(id: Int)
    fun selectedId(): Int?

    class Base(
        private val repository: PlayersRepository
    ) : PlayersUseCase {

        private var selectedId: Int? = null
        override val players: StateFlow<List<Player>> = repository.players
        override val activePlayers: StateFlow<List<Player>> = repository.activePlayers


        override suspend fun savePlayer(player: Player): Int = repository.savePlayer(player)

        override suspend fun updatePlayer(player: Player) {
            if (player.level > 0) repository.updatePlayer(player)
        }

        override suspend fun deletePlayer(id: Int) = repository.deletePlayer(id)

        override suspend fun player(id: Int) = repository.player(id)

        override fun selectId(id: Int) {
            selectedId = id
        }

        override fun selectedId() = selectedId


    }
}