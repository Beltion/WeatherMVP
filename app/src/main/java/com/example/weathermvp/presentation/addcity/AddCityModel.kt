package com.example.weathermvp.presentation.addcity

import android.util.Log
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.repositories.DayWeatherRepository
import kotlinx.coroutines.*

class AddCityModel {

    private val job = SupervisorJob()
    private val scope
            get() = CoroutineScope(Dispatchers.IO + job)
    private val TAG = AddCityModel::class.simpleName

    suspend fun getWeather(city:String): DayWeather{
        val dayWeather: DayWeather =  DayWeatherRepository.getWeather(city)
        Log.d(TAG, dayWeather.toString())
        return dayWeather
    }
}