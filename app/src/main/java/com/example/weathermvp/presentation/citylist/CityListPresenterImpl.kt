package com.example.weathermvp.presentation.citylist

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.CityListPresenter
import com.example.weathermvp.business.CityListView
import com.example.weathermvp.business.HttpErrorStrCoder
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.framework.room.City
import com.example.weathermvp.framework.room.CityDAO
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

class CityListPresenterImpl : CityListPresenter {

    private val TAG = CityListPresenterImpl::class.simpleName
    private var view: WeakReference<CityListView>? = null
    private val model = CityListModel()

    private val job = SupervisorJob()
    private val scopeMain
        get() = CoroutineScope(Dispatchers.Main + job)
    private val scopeIO
        get() = CoroutineScope(Dispatchers.IO + job)

    override fun initV(v: CityListView) {
        view = WeakReference(v)
    }

    override fun onItemClick(cityName: String) {
        Log.d(TAG, "onItemClick")
    }

    override fun onItemLongClick(cityName: String, position: Int) {
        view?.get()?.let { v ->
            scopeMain.launch {
                withContext(scopeIO.coroutineContext){
                    model.deleteCity(cityName, v.getRoomDbDao())
                }
                v.removeRvDayWeatherItem(position)
                val cities = withContext(scopeIO.coroutineContext){
                    model.getCities(v.getRoomDbDao())
                }
                Log.d(TAG, "Room items size: ${cities.size}")
                if (cities.size == 0) v.startAddCityActivity()
            }

        }
    }

    override fun onStartAddCityWeather(cityName: String) {
        view?.get()?.let { v->
            scopeMain.launch {
                val notSaved = withContext(scopeIO.coroutineContext){
                    isNotSaved(cityName, v.getRoomDbDao())
                }
                if (notSaved){
                    Log.d(TAG, "City from AddDialog may be saved")
                    v.hideContent()
                    val city = City(cityName)
                    withContext(scopeIO.coroutineContext){model.saveCity(city, v.getRoomDbDao())}
                    val weatherResult = withContext(scopeIO.coroutineContext){model.getWeather(cityName)}

                    when(weatherResult){
                        is DayWeatherApiResultWrapper.Success -> {
                            Log.d(TAG, "Good request $city: ${weatherResult.value.name}")
                            v.addRvDayWeatherItem(weather = weatherResult.value)
                        }
                        is DayWeatherApiResultWrapper.NetworkError -> {
                            Log.e(TAG, "Network error on get $city")
                            v.showContent()
                            v.showToast(v.getStringFromID(R.string.no_ethernet))
                        }
                        is DayWeatherApiResultWrapper.Error -> {
                            Log.e(TAG, "Get $city problem -> Code: ${weatherResult.code} Error: ${weatherResult.mess}")
                            v.showContent()
                            v.showToast(
                                    v.getStringFromID(HttpErrorStrCoder().getHttpErrorStrCode(weatherResult.code))
                            )
                        }
                    }

                    Log.d(TAG, "After hide content")
                } else {
                    Log.d(TAG, "City from AddDialog shouldn't be saved")
                    v.showToast(v.getStringFromID(R.string.already_saved))
                    v.showAddDialog(cityName)
                }
                v.showContent()
            }
        }
    }

    private suspend fun isNotSaved(cityName: String, roomDbDao: CityDAO): Boolean {
        val cities: ArrayList<City> = model.getCities(roomDbDao)
        var notSaved = true
        val lowerCityName = cityName.toLowerCase(Locale.ROOT)
        for(city in cities) {
            if (lowerCityName == city.city.toLowerCase(Locale.ROOT)) {
                notSaved = false
                break
            }
        }
        return notSaved
    }

    override fun onCreateView() {
        view?.get()?.let { v ->
            scopeMain.launch {
                val cities = withContext(scopeIO.coroutineContext){
                    model.getCities(v.getRoomDbDao())
                }
                Log.d(TAG, "Cities in Room:")
                cities.forEach {
                    Log.d(TAG, it.city)
                }
                if (cities.size > 0){
                    val citiesWeatherResult = withContext(scopeIO.coroutineContext){
                        model.getCitiesWeather(cities)
                    }
                    Log.d(TAG, "After load weathers")
                    val citiesWeather = ArrayList<DayWeather>()

//                    I used this flags for check error in load cities weather
//                    and if error was I check load something or all requests was with error
                    var somethingWrong = false
                    var somethingSuccess = false

                    citiesWeatherResult.forEach {
                        when(it){
                            is DayWeatherApiResultWrapper.Success -> {
                                somethingSuccess = true
                                citiesWeather.add(it.value)
                            }
                            is DayWeatherApiResultWrapper.Error -> {
                                somethingWrong = true
                            }
                            is DayWeatherApiResultWrapper.NetworkError -> {
                                somethingWrong = true
                            }
                        }
                    }
//                    It's not good idea
//                    First of all need show all message at all case, but not one message at all case
//                    And must be more beautiful way to do that, but for now I don't know how...
                    if (somethingSuccess){
                        if(somethingWrong){
                            v.showToast(v.getStringFromID(R.string.not_load))
                        }
                        v.initWeathersList(citiesWeather)
                    } else {
                        v.showToast(v.getStringFromID(R.string.not_load))
                    }
                }else{
                    Log.d(TAG, "No cities. Start AddCityActivity")
                    v.startAddCityActivity()
                }
                Log.d(TAG, "Before show content")
                v.showContent()
            }
        }
    }

}