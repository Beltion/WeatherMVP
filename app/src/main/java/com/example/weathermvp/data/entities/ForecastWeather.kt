package com.example.weathermvp.data.entities

data class ForecastWeather(
        val cod: Int,
        val city: CityName,
        val list: ArrayList<ForecastList>
)
