package com.exxuslee.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.exxuslee.data.utils.Constants
import com.exxuslee.domain.model.Sex


@Entity(tableName = Constants.TABLE)
data class Entity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "level") val level: Int,
    @ColumnInfo(name = "bonus") val bonus: Int,
    @ColumnInfo(name = "sex") val sex: Boolean,
    @ColumnInfo(name = "active") val active: Boolean,
)