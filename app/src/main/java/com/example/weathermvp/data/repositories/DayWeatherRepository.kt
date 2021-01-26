package com.example.weathermvp.data.repositories

import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.framework.WeatherAPI


object DayWeatherRepository {
    suspend fun getWeather(city: String): DayWeather = WeatherAPI.Common.retrofitService.getWeather(city)

    suspend fun saveCity(dayWeather: DayWeather){

    }
}