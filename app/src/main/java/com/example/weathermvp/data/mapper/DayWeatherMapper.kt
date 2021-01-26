package com.example.weathermvp.data.mapper

import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.framework.room.City

class DayWeatherMapper {
    fun dayWeatherToCity(dayWeather: DayWeather) = City(city = dayWeather.name)
}