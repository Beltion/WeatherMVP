package com.example.weathermvp.framework.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
class City(
        @PrimaryKey @ColumnInfo(name = "city")
        val city: String
)