package com.example.weathermvp.presentation.citylist

import com.example.weathermvp.data.repositories.CityRoomRepository
import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO

class CityListModel {

    suspend fun deleteCity(cityName: String, roomDbDao: CityDAO) {

    }

    suspend fun getCities(roomDbDao: CityDAO): ArrayList<City> {
        return ArrayList(CityRoomRepository(roomDbDao).getAllCities())
    }
}