package com.example.weathermvp.presentation.forecast

import android.util.Log
import com.example.weathermvp.business.ForecastPresenter
import com.example.weathermvp.business.ForecastView
import com.example.weathermvp.data.entities.ForecastDay
import com.example.weathermvp.data.entities.ForecastListItem
import com.example.weathermvp.data.entities.ForecastWeather
import com.example.weathermvp.data.entities.ForecastWeatherResutlWrapper
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class ForecastPresenterImpl : ForecastPresenter {

    private val TAG = ForecastPresenter::class.simpleName
    private var view: WeakReference<ForecastView>? = null
    private val model = ForecastModel()

    private val job = SupervisorJob()
    private val scopeMain
        get() = CoroutineScope(Dispatchers.Main + job)
    private val scopeIO
        get() = CoroutineScope(Dispatchers.IO + job)

    override fun initV(v: ForecastView) {
        view = WeakReference(v)
    }

    override fun onDayClick(forecastPosition: Int) {
        Log.d(TAG, " Forecast Day Position: $forecastPosition")
        Log.d(TAG, " Forecast Day WEATHER: ${model.forecastDays[forecastPosition].weather}")
        view?.get()?.setWeatherInThreeHourList(model.forecastDays[forecastPosition].weather)
    }

    override fun onCreateView() {
        view?.get()?.let{ v->
            val city = v.getParcelableCityName()
            scopeMain.launch {
                val forecastWeather = withContext(scopeIO.coroutineContext){
                    model.getForecast(city)
                }
                when(forecastWeather){
                    is ForecastWeatherResutlWrapper.Success -> {
                        Log.d(TAG, "Forecast OK")
                        Log.d(TAG, "Forecast ${forecastWeather.value.city}")
                        forecastWeatherToDaysAndWeather(forecastWeather.value)

                        v.initDaysRv(model.days)
                        v.initWeatherInThreeHourRv(model.forecastDays[0].weather)
                        v.showContent()
                    }
                    is ForecastWeatherResutlWrapper.Error -> {
                        Log.d(TAG, "Forecast Error: ${forecastWeather.code} ${forecastWeather.mess}")
                    }
                    is ForecastWeatherResutlWrapper.NetworkError -> {
                        Log.d(TAG, "Forecast network error")
                    }
                }
            }

        }
    }

    fun forecastWeatherToDaysAndWeather(forecastWeather: ForecastWeather){
        val days = ArrayList<String>()
        val forecastDays = ArrayList<ForecastDay>()
        var day = "${forecastWeather.list[0].dt_txt.substring(8,10)}.${forecastWeather.list[0].dt_txt.substring(5,7)}"
        days.add(day)
        val currentDayWeather = ArrayList<ForecastListItem>()

        forecastWeather.list.forEach { itemWeather ->
            val nextDate = "${itemWeather.dt_txt.substring(8,10)}.${itemWeather.dt_txt.substring(5,7)}"
            Log.d(TAG, "|$day| == |$nextDate|")
            if (day == nextDate){
                Log.d(TAG, "YES")
                currentDayWeather.add(itemWeather)
            } else {
                /*If I don't used bufList and if write
                * forecastDay = ForecastDay(day, currentDayWeather)
                * it will not work. All forecastDays elements.weather
                * will be like last element.weather
                * I think it's because currentDayWeather in ForecastDay(day, currentDayWeather)
                * does not pass data(although it should) but passes a reference to itself*/
                val bufList = ArrayList<ForecastListItem>()
                bufList.addAll(currentDayWeather)
                val forecastDay = ForecastDay(day, bufList)
                currentDayWeather.clear()
                Log.d(TAG, "Add forecastDay: $forecastDay")
                forecastDays.add(forecastDay)
                day = nextDate
                days.add(day)
                Log.d(TAG, "forecastDays")

                currentDayWeather.add(itemWeather)
            }
        }

        val forecastDay = ForecastDay(day, currentDayWeather)
        forecastDays.add(forecastDay)

        forecastDays.forEach {
            Log.d(TAG, "In ${it.day} weather: ${it.weather}")
        }

        model.days.addAll(days)
        model.forecastDays.addAll(forecastDays)
    }
}