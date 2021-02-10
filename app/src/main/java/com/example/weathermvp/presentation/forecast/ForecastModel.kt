package com.example.weathermvp.presentation.forecast

import com.example.weathermvp.data.entities.ForecastWeatherResutlWrapper
import com.example.weathermvp.data.repositories.ForecastWeatherRepository
import java.lang.reflect.Type

class ForecastModel {
    suspend fun getForecast(cityName: String): ForecastWeatherResutlWrapper<Type > {
        return ForecastWeatherRepository().getForecast(cityName)
    }
}