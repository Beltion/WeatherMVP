package com.example.weathermvp.presentation.citylist

import android.util.Log
import com.example.weathermvp.business.CityListPresenter
import com.example.weathermvp.business.CityListView
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

                v.showContent()
            }
        }
    }
}