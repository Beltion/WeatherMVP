package com.example.weathermvp.business

import com.example.weathermvp.data.entities.ForecastWeather


interface ForecastPresenter: BasePresenter {
    fun initV(v: ForecastView)
    fun onDayClick(forecastPosition: Int)

}

interface ForecastView: BaseVIew {
    fun setDaysList(days: ArrayList<String>)
    fun setWeatherInThreeHourList(weatherInThreeHour: ArrayList<ForecastWeather>)
    fun getStringFromID(stringID: Int) : String
    fun showToast(str: String)
    fun getParcelableCityName(): String
}