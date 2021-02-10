package com.example.weathermvp.data.repositories

import com.example.weathermvp.data.datasource.ForecastWeatherApiDataSource
import com.example.weathermvp.data.entities.ForecastWeatherResutlWrapper
import java.lang.reflect.Type

class ForecastWeatherRepository {
    suspend fun getForecast(cityName: String): ForecastWeatherResutlWrapper<Type> {
        val forecastDataSource = ForecastWeatherApiDataSource()
        return forecastDataSource.getForecast(cityName)
    }
}