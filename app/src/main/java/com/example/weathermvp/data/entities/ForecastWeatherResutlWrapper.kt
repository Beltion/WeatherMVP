package com.example.weathermvp.data.entities

import java.lang.reflect.Type

sealed class ForecastWeatherResutlWrapper<out Type>{
    data class Success(val value: ForecastWeather): ForecastWeatherResutlWrapper<Type>()
    data class Error(val code: Int? = null, val mess: String? = null): ForecastWeatherResutlWrapper<Nothing>()
    object NetworkError: ForecastWeatherResutlWrapper<Nothing>()
}
