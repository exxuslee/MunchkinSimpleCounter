package com.exxuslee.data.repositories

import com.exxuslee.data.local.dao.DAO
import com.exxuslee.data.mapper.BaseMapper
import com.exxuslee.domain.model.Player
import com.exxuslee.domain.repositories.Repository
import com.exxuslee.domain.utils.Result

class RepositoryImpl(
    private val dao: DAO,
) : Repository {
    private val mapper = BaseMapper.Base()

    
    override suspend fun getPlayer(id: Int): Result<Player> {
        TODO("Not yet implemented")
    }

    override suspend fun setPlayer(id: Int) {
        TODO("Not yet implemented")
    }
}