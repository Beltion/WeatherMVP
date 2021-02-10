package com.example.weathermvp.data.datasource

import com.example.weathermvp.data.entities.ForecastWeatherResutlWrapper
import com.example.weathermvp.framework.WeatherAPI
import retrofit2.HttpException
import java.io.IOException
import java.lang.reflect.Type

class ForecastWeatherApiDataSource {
    suspend fun getForecast(cityName: String): ForecastWeatherResutlWrapper<Type> {
        return try {
            val forecast = WeatherAPI.Common.retrofitService.getForecast(cityName)
            ForecastWeatherResutlWrapper.Success(forecast)
        } catch (t: Throwable){
            when(t){
                is IOException -> ForecastWeatherResutlWrapper.NetworkError
                is HttpException -> ForecastWeatherResutlWrapper.Error(t.code(), t.message())
                else -> {
                    t.printStackTrace()
                    ForecastWeatherResutlWrapper.Error(mess = t.message)
                }
            }
        }
    }
}