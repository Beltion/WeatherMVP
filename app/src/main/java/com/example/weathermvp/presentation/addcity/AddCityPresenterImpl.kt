package com.example.weathermvp.presentation.addcity

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.AddCityPresenter
import com.example.weathermvp.business.AddCityView
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.data.entities.Weather
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class AddCityPresenterImpl : AddCityPresenter {

    private val job = SupervisorJob()
    private val scope
        get() = CoroutineScope(Dispatchers.Main + job)
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
                    scope.launch {
                        Log.d(TAG, "Get city:$city")
                        val dayWeather: DayWeather = model.getWeather(city)
                        Log.d(TAG, "Mess after coroutines: $dayWeather")
                        
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }

            } else {
                v.showToast(
                        v.getStringFromID(R.string.input_city_name)
                )
            }
        } ?: Log.d(TAG, "View null")

    }

    override fun onCreateView() {
        view?.get()?.let {v ->
            v.showContent()

        }
    }
}