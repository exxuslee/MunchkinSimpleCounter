package com.exxuslee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxuslee.data.local.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDAO {

    @Query("SELECT * FROM Players WHERE name = :name")
    fun player(name: String): PlayerEntity?

    @Query("SELECT * FROM Players")
    suspend fun players(): List<PlayerEntity>

    @Query("SELECT * FROM players")
    fun playersFlow(): Flow<List<PlayerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayer(player: PlayerEntity)

    @Query("UPDATE Players SET level = :level, bonus = :bonus, reverseSex = :reverseSex, active = :active WHERE id = :id")
    suspend fun updatePlayer(
        id: Int,
        level: Int,
        bonus: Int,
        reverseSex: Boolean,
        active: Boolean,
    )

    @Query("SELECT MAX (id) FROM Players")
    suspend fun lastID(): Int?

    @Query("DELETE FROM Players WHERE id = :id")
    suspend fun deletePlayer(id: Int)
}
