package com.example.weathermvp.presentation.citylist.adddialog

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.AddDialogPresenter
import com.example.weathermvp.business.AddDialogView
import java.lang.ref.WeakReference

class AddDialogPresenterImpl: AddDialogPresenter {

    private val TAG = AddDialogPresenterImpl::class.simpleName
    private var view: WeakReference<AddDialogView>? = null

    override fun initV(v: AddDialogView) {
        view = WeakReference(v)
    }

    override fun onPositiveBtnClick(city: String) {
        Log.d(TAG, "onPositiveBtnClick: $city")
        view?.get()?.let { v->
            if (city.isNotBlank() && city.isNotEmpty()){
                v.startAddCityWeather(city)
            } else {
                v.showToast(v.getStringFromID(R.string.input_city_name))
                v.startAddCityWeather(city)
            }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
    }

    override fun onCancel() {
        Log.d(TAG, "onCancel")
    }
}