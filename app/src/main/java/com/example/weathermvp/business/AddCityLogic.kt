package com.example.weathermvp.business

import com.example.weathermvp.framework.room.CityDAO

//  AddCity activity used for get city weather from city name
//  and save it in Room

interface AddCityPresenter : BasePresenter {
    fun initV(v: AddCityView)
    fun onBtnClick()
}

interface AddCityView : BaseVIew {
    fun getCityName() : String
    fun getStringFromID(stringID: Int) : String
    fun showToast(str: String)
    fun startNewActivity()
    fun getRoomDbDao(): CityDAO
}
