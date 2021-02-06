package com.example.weathermvp.business

import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.framework.room.CityDAO
import java.util.ArrayList


interface CityListPresenter : BasePresenter {
    fun initV(v: CityListView)
    fun onItemClick(cityName: String)
    fun onItemLongClick(cityName: String, position: Int)
}

interface CityListView : BaseVIew {
    fun getStringFromID(stringID: Int) : String
    fun showToast(str: String)
    fun startForecastActivity()
    fun startAddCityActivity()
    fun getRoomDbDao(): CityDAO
    fun initWeathersList(citiesWeather: ArrayList<DayWeather>)
    fun addRvDayWeatherItem(weather: DayWeather)
    fun removeRvDayWeatherItem(position: Int)
}