package com.example.weathermvp.framework.room

import androidx.room.*

@Dao
interface CityDAO {

    @Query("SELECT * FROM cities")
    suspend fun getCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City)

    @Delete
    suspend fun delete(city: City)
}