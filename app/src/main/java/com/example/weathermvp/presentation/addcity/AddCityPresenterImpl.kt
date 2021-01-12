package com.example.weathermvp.presentation.addcity

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
        TODO("Not yet implemented")
    }

    override fun inCreateView() {
        TODO("Not yet implemented")
    }
}