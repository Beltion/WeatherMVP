package com.example.weathermvp.data.entities

import com.google.gson.annotations.SerializedName

data class Weather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
)

data class WeatherInDetail(
        val temp: Float,
        val feels_like: Float,
        val temp_min: Float,
        val temp_max: Float,
        val pressure: Float,
        val humidity: Int
)

data class Wind(
        val speed: Float,
        val deg: Int
)

data class Rain(
        @SerializedName("1h")
        val atHour: Float,
        @SerializedName("3h")
        val at3Hour: Float
)

data class Snow(
        @SerializedName("1h")
        val atHour: Float,
        @SerializedName("3h")
        val at3Hour: Float
)

data class Clouds(
        val all: Int
)

data class CityName(
        val name: String
)

data class ForecastList(
        val weather: ArrayList<Weather>,
        val main: WeatherInDetail,
        val dt_txt: String
)