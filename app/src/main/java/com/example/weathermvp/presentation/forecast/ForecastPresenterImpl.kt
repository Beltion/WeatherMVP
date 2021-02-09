package com.example.weathermvp.presentation.forecast

import com.example.weathermvp.business.ForecastPresenter
import com.example.weathermvp.business.ForecastView
import java.lang.ref.WeakReference

class ForecastPresenterImpl : ForecastPresenter {

    private val TAG = ForecastPresenter::class.simpleName
    private var view: WeakReference<ForecastView>? = null

    override fun initV(v: ForecastView) {
        view = WeakReference(v)
    }

    override fun onDayClick(forecastPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateView() {
        view?.get()?.let{ v->

        }
    }
}