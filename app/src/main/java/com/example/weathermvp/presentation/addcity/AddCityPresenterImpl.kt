package com.example.weathermvp.presentation.addcity

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.AddCityPresenter
import com.example.weathermvp.business.AddCityView
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.DayWeatherApiResultWrapper
import com.example.weathermvp.data.mapper.DayWeatherMapper
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.lang.reflect.Type

class AddCityPresenterImpl : AddCityPresenter {

    private val job = SupervisorJob()
    private val scopeMain
        get() = CoroutineScope(Dispatchers.Main + job)
    private val scopeIO
        get() = CoroutineScope(Dispatchers.IO + job)
    private val TAG = AddCityPresenterImpl::class.simpleName
    private var view: WeakReference<AddCityView>? = null
    private val model = AddCityModel()

    override fun initV(v: AddCityView) {
        view = WeakReference(v)
    }

    override fun onBtnClick() {
        Log.d(TAG, "btn click")
        view?.get()?.let {v ->
            val city = v.getCityName()

            if(city.isNotBlank() && city.isNotEmpty()){
                v.hideContent()
                try{
                    scopeMain.launch {
                        Log.d(TAG, "Get city:$city")
                        val dayWeather: DayWeatherApiResultWrapper<Type> = withContext(scopeIO.coroutineContext){
                            model.getWeather(city)
                        }

                        when(dayWeather){
                            is DayWeatherApiResultWrapper.Success -> {
                                Log.d(TAG, "Good request $city: ${dayWeather.value.name}")
                                startSaveCity(dayWeather.value)
                            }
                            is DayWeatherApiResultWrapper.NetworkError -> {
                                Log.e(TAG, "Network error on get $city")
                                v.showContent()
                                v.showToast(v.getStringFromID(R.string.no_ethernet))
                            }
                            is DayWeatherApiResultWrapper.Error -> {
                                Log.e(TAG, "Get $city problem -> Code: ${dayWeather.code} Error: ${dayWeather.mess}")
                                v.showContent()
                                onHttpError(dayWeather.code)

                            }
                        }
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }

            } else {
                v.showToast(
                        v.getStringFromID(R.string.input_city_name)
                )
            }
        } ?: Log.e(TAG, "View null")

    }

    private fun onHttpError(code: Int?) {
        view?.get()?.let{ v->
            when(code){
                404 -> v.showToast(v.getStringFromID(R.string.http404))
                408 -> v.showToast(v.getStringFromID(R.string.http408))
                500 -> v.showToast(v.getStringFromID(R.string.http500))
                503 -> v.showToast(v.getStringFromID(R.string.http503))
                else -> v.showToast(v.getStringFromID(R.string.not_load))
            }
        }
    }

    private fun startSaveCity(dayWeather: DayWeather?) {
        view?.get()?.let{ v->
            dayWeather?.let {
                val city = DayWeatherMapper().dayWeatherToCity(dayWeather = dayWeather)
                val dao = v.getRoomDbDao()
                scopeMain.launch {
                    withContext(scopeIO.coroutineContext){model.saveCity(city, dao)}
                    v.startCityListActivity()
                }
            } ?: v.showToast(v.getStringFromID(R.string.not_load))
        }
    }

    override fun onCreateView() {
        view?.get()?.let {v ->
            v.showContent()

        }
    }
}