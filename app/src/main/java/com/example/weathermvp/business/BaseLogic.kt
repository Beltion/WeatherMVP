package com.example.weathermvp.business

interface BasePresenter{
    fun inCreateView()
}

interface BaseVIew{
    fun initViews()
    fun initLogicItem()
}
