package com.example.weathermvp.presentation.addcity

import android.util.Log
import com.example.weathermvp.R
import com.example.weathermvp.business.AddCityPresenter
import com.example.weathermvp.business.AddCityView
import java.lang.ref.WeakReference

class AddCityPresenterImpl : AddCityPresenter {

    private val TAG = AddCityPresenterImpl::class.simpleName
    private var view: WeakReference<AddCityView>? = null

    override fun initV(v: AddCityView) {
        view = WeakReference(v)
    }

    override fun onBtnClick() {
        view?.get()?.let {v ->
            val city = v.getCityName()

            if(city.isNotBlank() && city.isNotEmpty()){
                Log.d(TAG, "Get city:$city")
            } else {
                v.showToast(
                        v.getStringFromID(R.string.input_city_name)
                )
            }
        }

    }

    override fun onCreateView() {
        view?.get()?.let {v ->
            v.showContent()

        }
    }
}