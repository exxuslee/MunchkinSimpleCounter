package com.exxuslee.data.repositories

import com.exxuslee.data.local.dao.PlayerDAO
import com.exxuslee.data.mapper.BaseMapper
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.Repository
import com.exxuslee.domain.utils.Result

class RepositoryImpl(
    private val playerDAO: PlayerDAO,
) : Repository {
    private val mapper = BaseMapper.Base()


    override suspend fun loadPlayer(name: String): Result<Player> {
        val localData = playerDAO.player(name)
        return if (localData != null) Result.Success(mapper.oneToDomain(localData))
        else Result.Error("no player in db")
    }

    override suspend fun savePlayer(player: Player) {
        playerDAO.savePlayer(mapper.domainToLocal(player))
    }

    override suspend fun updatePlayer(player: Player) {
        playerDAO.updatePlayer(player.level, player.bonus, player.reverseSex, player.playing, player.name)
    }

    override suspend fun players(): Result<List<Player>> {
        val localData = playerDAO.players()
        return if (localData != null) Result.Success(mapper.allToDomain(localData))
        else Result.Error("no players in db")
    }
}