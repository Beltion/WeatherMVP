package com.example.weathermvp.presentation.citylist

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.CityListPresenter
import com.example.weathermvp.business.CityListView
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

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

    override fun onItemLongClick(cityName: String) {
        view?.get()?.let { v ->
            scopeMain.launch {
                withContext(scopeIO.coroutineContext){
                    model.deleteCity(cityName, v.getRoomDbDao())
                }
            }

        }
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

//                    I used this flag for check error in load cities weather
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
                    v.startAddCityActivity()
                }
                Log.d(TAG, "Before show content")
                v.showContent()
            }
        }
    }
}