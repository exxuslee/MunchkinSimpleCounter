package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.PlayersRepository

interface PlayersUseCase {
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player)
    suspend fun players(): List<Player>
    suspend fun deletePlayer(id: Int)

    class Base(private val repository: PlayersRepository) : PlayersUseCase {

        override suspend fun savePlayer(player: Player): Int = repository.savePlayer(player)

        override suspend fun updatePlayer(player: Player) {
            if (player.level > 0) repository.updatePlayer(player)
        }

        override suspend fun players(): List<Player> = repository.players()

        override suspend fun deletePlayer(id: Int) = repository.deletePlayer(id)

    }
}