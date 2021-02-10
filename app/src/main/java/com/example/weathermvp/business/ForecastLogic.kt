package com.example.weathermvp.business

import com.example.weathermvp.data.entities.ForecastListItem
import com.example.weathermvp.data.entities.ForecastWeather


interface ForecastPresenter: BasePresenter {
    fun initV(v: ForecastView)
    fun onDayClick(forecastPosition: Int)

}

interface ForecastView: BaseVIew {
    fun initDaysRv(days: ArrayList<String>)
    fun initWeatherInThreeHourRv(weatherInThreeHour: ArrayList<ForecastListItem>)
    fun setWeatherInThreeHourList(weatherInThreeHour: ArrayList<ForecastListItem>)
    fun getStringFromID(stringID: Int) : String
    fun showToast(str: String)
    fun getParcelableCityName(): String
}