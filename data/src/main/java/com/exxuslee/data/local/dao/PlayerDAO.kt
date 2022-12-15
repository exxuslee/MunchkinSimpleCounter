package com.exxuslee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxuslee.data.local.entities.Entity

@Dao
interface PlayerDAO {

    @Query("SELECT * FROM table_number WHERE name = :name")
    fun player(name: String): Entity?

    @Query("SELECT * FROM table_number")
    fun players(): List<Entity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePlayer(player: Entity)

    @Query("UPDATE table_number SET level = :level, bonus = :bonus, reverseSex = :reverseSex, active = :active WHERE id = :id")
    suspend fun updatePlayer(
        id: Int,
        level: Int,
        bonus: Int,
        reverseSex: Boolean,
        active: Boolean,
    )

    @Query("SELECT MAX (id) FROM table_number")
    suspend fun lastID(): Int?
}
