package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.Repository
import com.exxuslee.domain.utils.Result

interface UseCase {
    suspend fun loadPlayer(name: String): Result<Player>
    suspend fun savePlayer(player: Player)
    suspend fun updatePlayer(player: Player)
    suspend fun players(): Result<List<Player>>

    class Base(private val repository: Repository) : UseCase {
        override suspend fun loadPlayer(name: String): Result<Player> =
            repository.loadPlayer(name)

        override suspend fun savePlayer(player: Player){
            repository.savePlayer(player)
        }

        override suspend fun updatePlayer(player: Player) {
            repository.updatePlayer(player)
        }

        override suspend fun players(): Result<List<Player>> =
            repository.players()
    }
}