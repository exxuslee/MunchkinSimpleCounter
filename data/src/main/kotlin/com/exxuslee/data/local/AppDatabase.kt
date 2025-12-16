package com.exxuslee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exxuslee.data.local.dao.PlayerDAO
import com.exxuslee.data.local.dao.TokensDAO
import com.exxuslee.data.local.entities.PlayerEntity
import com.exxuslee.data.local.entities.TokenEntity


@Database(entities = [PlayerEntity::class, TokenEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val playerDAO: PlayerDAO
    abstract val tokensDAO: TokensDAO
}