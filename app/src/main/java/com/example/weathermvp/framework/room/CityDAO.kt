package com.example.weathermvp.framework.room

import androidx.room.*

@Dao
interface CityDAO {

    @Query("SELECT * FROM cities")
    suspend fun getCities(): List<City>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: City)

    @Query("DELETE FROM cities WHERE city=:city")
    suspend fun delete(city: String)
}