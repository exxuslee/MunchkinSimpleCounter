package com.exxuslee.data.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exxuslee.data.localDB.dao.PlayerDAO
import com.exxuslee.data.localDB.entities.Entity


@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: PlayerDAO
}