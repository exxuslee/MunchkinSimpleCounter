package com.exxuslee.domain.repositories

import com.exxuslee.domain.model.Player
import com.exxuslee.domain.utils.Result

interface Repository {
    suspend fun getPlayer(id: Int): Result<Player>
    suspend fun setPlayer(id: Int)
}