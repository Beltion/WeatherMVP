package com.example.weathermvp.data.repositories

import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO

class CityRoomRepository(private val cityDAO: CityDAO) {

    suspend fun saveCity(city: City){
        cityDAO.insert(city)
    }

    suspend fun getAllCities() = cityDAO.getCities()

    suspend fun deleteCity(city: String) = cityDAO.delete(city)
}