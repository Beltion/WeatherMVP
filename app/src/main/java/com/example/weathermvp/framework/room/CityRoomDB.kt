package com.example.weathermvp.framework.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [City::class], version = 1, exportSchema = false)
public abstract class CityRoomDB : RoomDatabase() {
    abstract fun getCityDao(): CityDAO

    companion object{
        @Volatile
        private var INSTANCE: CityRoomDB? = null

        fun getDatabase(context: Context): CityRoomDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CityRoomDB::class.java,
                        "city_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}