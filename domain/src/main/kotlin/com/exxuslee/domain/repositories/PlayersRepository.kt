package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.Player

interface PlayersRepository {
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player): Int
    suspend fun players(): List<Player>
    suspend fun deletePlayer(id: Int)
}