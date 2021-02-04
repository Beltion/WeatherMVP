package com.example.weathermvp.presentation.citylist

import android.util.Log
import com.example.weathermvp.business.CityListPresenter
import com.example.weathermvp.business.CityListView
import com.example.weathermvp.framework.room.City
import java.lang.ref.WeakReference

class CityListPresenterImpl : CityListPresenter {

    private val TAG = CityListPresenterImpl::class.simpleName
    private var view: WeakReference<CityListView>? = null
    private val model = CityListModel()

    override fun initV(v: CityListView) {
        view = WeakReference(v)
    }

    override fun onItemClick(cityName: String) {
        Log.d(TAG, "onItemClick")
    }

    override fun onItemLongClick(cityName: String) {
        model.deleteCity(cityName)
    }

    override fun onCreateView() {
        TODO("Not yet implemented")
    }
}