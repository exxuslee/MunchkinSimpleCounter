package com.exxuslee.domain.usecases

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.Repository
import com.exxuslee.domain.utils.Result

interface UseCase {
    suspend fun getPlayer(id: Int): Result<Player>
    suspend fun setPlayer(id: Int)
    suspend fun players(): Result<List<Player>>

    class Base(private val repository: Repository) : UseCase {
        override suspend fun getPlayer(id: Int): Result<Player> =
            repository.getPlayer(id)

        override suspend fun setPlayer(id: Int){
            repository.setPlayer(id)
        }

        override suspend fun players(): Result<List<Player>> =
            repository.players()
    }
}