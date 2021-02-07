package com.example.weathermvp.presentation.citylist

import android.util.Log
import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.data.repositories.CityRoomRepository
import com.example.weathermvp.data.repositories.DayWeatherRepository
import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO
import java.lang.Exception
import java.lang.reflect.Type

class CityListModel {

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

    suspend fun deleteCity(cityName: String, roomDbDao: CityDAO) {
        CityRoomRepository(cityDAO = roomDbDao).deleteCity(cityName)
    }

    suspend fun getCities(roomDbDao: CityDAO): ArrayList<City> {
        return ArrayList(CityRoomRepository(roomDbDao).getAllCities())
    }

    suspend fun getCitiesWeather(cities: ArrayList<City>):ArrayList<DayWeatherApiResultWrapper<Type>> {
        val citiesWeather = ArrayList<DayWeatherApiResultWrapper<Type>>()
        cities.forEach {
            val cityWeather = DayWeatherRepository.getWeather(it.city)
            Log.d("CITY", "${it.city}: $cityWeather")
            citiesWeather.add(cityWeather)
        }
        return citiesWeather
    }
}