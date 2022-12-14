package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.utils.Result

interface Repository {
    suspend fun loadPlayer(name: String): Result<Player>
    suspend fun savePlayer(player: Player):Int
    suspend fun updatePlayer(player: Player): Int
    suspend fun players():Result<List<Player>>
}