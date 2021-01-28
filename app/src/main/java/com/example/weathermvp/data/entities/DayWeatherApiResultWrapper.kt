package com.example.weathermvp.data.entities

import java.lang.reflect.Type

sealed class DayWeatherApiResultWrapper<out Type> {
    data class Success(val value: DayWeather): DayWeatherApiResultWrapper<Type>()
    data class Error(val code: Int? = null, val mess: String? = null): DayWeatherApiResultWrapper<Nothing>()
    object NetworkError: DayWeatherApiResultWrapper<Nothing>()
}