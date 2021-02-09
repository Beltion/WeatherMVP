package com.example.weathermvp.framework

import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.ForecastWeather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI{
    @GET("weather?")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric",   //  Temperature in Celsius
        @Query("appid") key: String = "51d4575d9c8e6d6decb5854c1db07ec7"
    ): DayWeather

    @GET("forecast?")
    suspend fun getForecast(
            @Query("q") city: String,
            @Query("lang") lang: String = "ru",
            @Query("units") units: String = "metric",
            @Query("appid") key: String = "51d4575d9c8e6d6decb5854c1db07ec7"
    ): ForecastWeather

    object RetrofitClient{
        private var retrofit: Retrofit? = null
        fun getRetrofit(baseUrl: String): Retrofit{
            if (retrofit == null){
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }

    object Common{
        private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        val retrofitService: WeatherAPI
            get() = RetrofitClient.getRetrofit(baseUrl = BASE_URL).create(WeatherAPI::class.java)
    }

}