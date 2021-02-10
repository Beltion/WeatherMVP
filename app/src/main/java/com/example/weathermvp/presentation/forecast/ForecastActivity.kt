package com.example.weathermvp.presentation.forecast

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.example.weathermvp.business.ForecastView
import com.example.weathermvp.data.entities.ForecastListItem

class ForecastActivity:
        ForecastView,
        ForecastDaysRvAdapter.OnForecastDaysClickListener,
        AppCompatActivity()
{

    private lateinit var content: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var cityName: TextView
    private lateinit var daysRv: RecyclerView
    private lateinit var weatherInTreeHourRv: RecyclerView

    private lateinit var presenter: ForecastPresenterImpl


    override fun onStart() {
        super.onStart()
        initLogicItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forecast)
        initViews()
    }

    override fun initDaysRv(days: ArrayList<String>) {
        val adapter = ForecastDaysRvAdapter(days, this)
        daysRv.adapter = adapter
    }

    override fun initWeatherInThreeHourRv(weatherInThreeHour: ArrayList<ForecastListItem>) {
        val adapter = ForecastWeatherRvAdapter(weathers = weatherInThreeHour)
        weatherInTreeHourRv.adapter = adapter
    }

    override fun setWeatherInThreeHourList(weatherInThreeHour: ArrayList<ForecastListItem>) {
        (weatherInTreeHourRv.adapter as ForecastWeatherRvAdapter).reSetList(weatherInThreeHour)
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
        content = findViewById(R.id.content_forecast)
        progressBar =findViewById(R.id.progressbar)
        daysRv = findViewById(R.id.rv_days_forecast)
        daysRv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.HORIZONTAL, false)
        weatherInTreeHourRv = findViewById(R.id.rv_weather_forecast)
        weatherInTreeHourRv.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
    }

    override fun initLogicItem() {
        presenter = ForecastPresenterImpl()
        presenter.initV(this)
        presenter.onCreateView()
    }

    override fun showContent() {
        content.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        content.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onDayClick(position: Int) {
        presenter.onDayClick(position)
    }
}