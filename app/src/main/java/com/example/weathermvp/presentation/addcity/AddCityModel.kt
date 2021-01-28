package com.example.weathermvp.presentation.addcity

import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.data.repositories.CityRoomRepository
import com.example.weathermvp.data.repositories.DayWeatherRepository
import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO
import java.lang.Exception
import java.lang.reflect.Type

class AddCityModel {

    private val TAG = AddCityModel::class.simpleName

    suspend fun getWeather(city:String): DayWeatherApiResultWrapper<Type>{
        return DayWeatherRepository.getWeather(city)
    }

    suspend fun saveCity(city: City, dao: CityDAO){
        try {
            CityRoomRepository(dao).saveCity(city)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}