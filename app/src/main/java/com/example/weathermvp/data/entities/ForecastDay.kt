package com.example.weathermvp.data.entities

data class ForecastDay(
        val day: String,
        val weather: ArrayList<ForecastListItem>
)
