package com.example.weathermvp.presentation.forecast

import com.example.weathermvp.data.entities.ForecastDay
import com.example.weathermvp.data.entities.ForecastWeatherResutlWrapper
import com.example.weathermvp.data.repositories.ForecastWeatherRepository
import java.lang.reflect.Type

class ForecastModel {
     var forecastDays = ArrayList<ForecastDay>()
     var days = ArrayList<String>()

    suspend fun getForecast(cityName: String): ForecastWeatherResutlWrapper<Type > {
        return ForecastWeatherRepository().getForecast(cityName)
    }
}