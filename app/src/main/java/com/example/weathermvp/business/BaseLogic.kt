package com.example.weathermvp.business

interface BasePresenter{
    fun onCreateView()
}

interface BaseVIew{
    fun initViews()
    fun initLogicItem()
    fun showContent()
    fun hideContent()
}
