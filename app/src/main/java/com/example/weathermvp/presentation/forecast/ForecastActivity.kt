package com.example.weathermvp.presentation.forecast

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.business.ForecastView
import com.example.weathermvp.data.entities.ForecastWeather

class ForecastActivity:
        ForecastView,
        AppCompatActivity()
{

    private lateinit var content: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var cityName: TextView
    private lateinit var daysRv: RecyclerView
    private lateinit var weatherInTreeHourRv: RecyclerView

    private lateinit var presenter: ForecastPresenterImpl
    private var city: String? = null


    override fun onStart() {
        super.onStart()
        initLogicItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun setDaysList(days: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setWeatherInThreeHourList(weatherInThreeHour: ArrayList<ForecastWeather>) {
        TODO("Not yet implemented")
    }

    override fun getStringFromID(stringID: Int)
        = getString(stringID)

    override fun showToast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun getParcelableCityName(): String {
        return intent.getStringExtra("city").toString()
    }

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun initLogicItem() {
        TODO("Not yet implemented")
    }

    override fun showContent() {
        content.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        content.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
}