package com.example.weathermvp.data.repositories

import com.example.weathermvp.data.datasource.DayWeatherApiDataSource
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.framework.WeatherAPI
import java.lang.reflect.Type


object DayWeatherRepository {
    suspend fun getWeather(city: String): DayWeatherApiResultWrapper<Type> {
        val dayWeatherApiDataSource = DayWeatherApiDataSource()
        return dayWeatherApiDataSource.getWeather(city)
    }

    suspend fun saveCity(dayWeather: DayWeather){

    }
}