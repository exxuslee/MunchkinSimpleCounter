package com.exxuslee.data.repositories

import com.exxuslee.data.localDB.dao.PlayerDAO
import com.exxuslee.data.mapper.BaseMapper
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.RepositoryDB
import com.exxuslee.domain.utils.Result

class RepositoryImpl(private val playerDAO: PlayerDAO) : RepositoryDB {
    private val mapper = BaseMapper.Base()

    override suspend fun loadPlayer(name: String): Result<Player> {
        val localData = playerDAO.player(name)
        return if (localData != null) Result.Success(mapper.oneToDomain(localData))
        else Result.Error("no player in db")
    }

    override suspend fun savePlayer(player: Player): Int {
        playerDAO.savePlayer(mapper.domainToLocal(player))
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

    override suspend fun players(): Result<List<Player>> {
        val localData = playerDAO.players()
        return if (localData != null) Result.Success(mapper.allToDomain(localData))
        else Result.Error("no players in db")
    }

    override suspend fun deletePlayer(id: Int) {
        playerDAO.deletePlayer(id)
    }
}