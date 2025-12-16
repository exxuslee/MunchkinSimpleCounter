package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.Player
import kotlinx.coroutines.flow.StateFlow

interface PlayersRepository {
    val players: StateFlow<List<Player>>
    suspend fun savePlayer(player: Player): Int
    suspend fun updatePlayer(player: Player): Int
    suspend fun deletePlayer(id: Int)
    suspend fun player(id: Int): Player?
}