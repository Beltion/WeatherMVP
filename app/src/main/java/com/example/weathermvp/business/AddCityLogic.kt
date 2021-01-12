package com.example.weathermvp.business

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
}
