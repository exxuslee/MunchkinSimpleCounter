package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.RepositoryDB
import com.exxuslee.domain.utils.Result

interface UseCaseDB {
    suspend fun loadPlayer(name: String): Result<Player>
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player)
    suspend fun players(): Result<List<Player>>
    suspend fun deletePlayer(id: Int)

    class Base(private val repository: RepositoryDB) : UseCaseDB {
        override suspend fun loadPlayer(name: String): Result<Player> =
            repository.loadPlayer(name)

        override suspend fun savePlayer(player: Player): Int = repository.savePlayer(player)

        override suspend fun updatePlayer(player: Player){
            if (player.level > 0) repository.updatePlayer(player)
        }

        override suspend fun players(): Result<List<Player>> =
            repository.players()

        override suspend fun deletePlayer(id: Int) {
            repository.deletePlayer(id)
        }
    }
}