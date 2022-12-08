package com.exxuslee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxuslee.data.local.entities.Entity

@Dao
interface PlayerDAO{

    @Query("SELECT * FROM table_number WHERE id = :id")
    fun player(id: Int): Entity?

    @Query("SELECT * FROM table_number")
    fun players(): List<Entity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayer(player: Entity)
}
