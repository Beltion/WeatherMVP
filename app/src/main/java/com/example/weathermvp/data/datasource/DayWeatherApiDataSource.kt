package com.example.weathermvp.data.datasource

import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.framework.WeatherAPI
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class DayWeatherApiDataSource {

    suspend fun getWeather(city: String): DayWeatherApiResultWrapper<Type>{
        return try {
            val dayWeather = WeatherAPI.Common.retrofitService.getWeather(city)
            DayWeatherApiResultWrapper.Success(dayWeather)
        } catch (t: Throwable){
            when(t){
                is IOException -> DayWeatherApiResultWrapper.NetworkError
                is HttpException ->{
                    DayWeatherApiResultWrapper.Error(t.code(), t.message())
                }
                else ->{
                    t.printStackTrace()
                    DayWeatherApiResultWrapper.Error(mess = t.message)
                }
            }
        }
    }
}