package com.exxuslee.data.repositories

import com.exxuslee.data.local.dao.PlayerDAO
import com.exxuslee.data.mapper.toData
import com.exxuslee.data.mapper.toDomain
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.PlayersRepository

class PlayersRepositoryImpl(private val playerDAO: PlayerDAO) : PlayersRepository {

    override suspend fun savePlayer(player: Player): Int {
        playerDAO.savePlayer(player.toData())
        return playerDAO.lastID() ?: -1
    }

    override suspend fun updatePlayer(player: Player): Int {
        playerDAO.updatePlayer(
            player.id,
            player.level,
            player.bonus,
            player.reverseSex,
            player.playing
        )
        return 0
    }

    override suspend fun players(): List<Player> {
        val localData = playerDAO.players()
        return localData.map { it.toDomain() }
    }

    override suspend fun deletePlayer(id: Int) {
        playerDAO.deletePlayer(id)
    }
}