package com.example.weathermvp.presentation.forecast

import android.util.Log
import com.example.weathermvp.business.ForecastPresenter
import com.example.weathermvp.business.ForecastView
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
        TODO("Not yet implemented")
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
}