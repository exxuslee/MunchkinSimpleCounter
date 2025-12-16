package com.exxuslee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exxuslee.data.local.dao.PlayerDAO
import com.exxuslee.data.local.dao.PriceDAO
import com.exxuslee.data.local.entities.PlayerEntity


@Database(entities = [PlayerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val playerDAO: PlayerDAO
    abstract val priceDAO: PriceDAO
}