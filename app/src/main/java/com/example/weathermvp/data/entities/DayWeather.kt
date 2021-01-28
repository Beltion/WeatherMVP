package com.example.weathermvp.data.entities

data class DayWeather(
        val cod: Int,
        val message: String,
        val name: String,
        val weather: ArrayList<Weather>,
        val main: WeatherInDetail,
        val clouds: Clouds,
        val wind: Wind,
        val snow: Snow?,
        val rain: Rain?
)