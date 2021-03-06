package com.example.weathermvp.business

import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.framework.room.CityDAO
import java.util.ArrayList


interface CityListPresenter : BasePresenter {
    fun initV(v: CityListView)
    fun onItemClick(cityName: String)
    fun onItemLongClick(cityName: String, position: Int)
    fun onStartAddCityWeather(cityName: String)
}

interface CityListView : BaseVIew {
    fun getStringFromID(stringID: Int) : String
    fun showToast(str: String)
    fun startForecastActivity(cityName: String)
    fun startAddCityActivity()
    fun getRoomDbDao(): CityDAO
    fun initWeathersList(citiesWeather: ArrayList<DayWeather>)
    fun addRvDayWeatherItem(weather: DayWeather)
    fun removeRvDayWeatherItem(position: Int)
    fun showAddDialog(cityName: String?)
    fun onDialogStartAddCityWeather(cityName: String)
}

interface AddDialogPresenter{
    fun initV(v: AddDialogView)
    fun onPositiveBtnClick(city: String)
    fun onDestroy()
    fun onCancel()
}

interface AddDialogView{
    fun showToast(str: String)
    fun getStringFromID(id: Int): String
    fun showDialogAgain(city: String)
    fun startAddCityWeather(city: String)

}
