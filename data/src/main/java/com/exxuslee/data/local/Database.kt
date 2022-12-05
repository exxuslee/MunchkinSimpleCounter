package com.exxuslee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exxuslee.data.local.dao.PlayerDAO
import com.exxuslee.data.local.entities.Entity


@Database(entities = [Entity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract val dao: PlayerDAO
}