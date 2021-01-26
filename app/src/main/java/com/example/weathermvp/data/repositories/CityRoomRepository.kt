package com.example.weathermvp.data.repositories

import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO

class CityRoomRepository(private val cityDAO: CityDAO) {
    private val TAG = CityRoomRepository::class.simpleName

    suspend fun saveCity(city: City){
        cityDAO.insert(city)
    }

    suspend fun getAllCities() = cityDAO.getCities()

    suspend fun deleteCity(city: City) = cityDAO.delete(city)
}