package com.example.weathermvp.presentation.citylist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.example.weathermvp.business.CityListView
import com.example.weathermvp.data.entities.DayWeather
import com.example.weathermvp.framework.room.CityDAO
import com.example.weathermvp.framework.room.CityRoomDB
import com.example.weathermvp.presentation.addcity.AddCityActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class CityListActivity:
        CityListView,
        CityListRvAdapter.OnCityListClickListener,
        AppCompatActivity()
{

    private lateinit var content: ConstraintLayout
    private lateinit var progress: ProgressBar
    private lateinit var rvDayWeather: RecyclerView
    private lateinit var fabCallAddCity: FloatingActionButton

    private lateinit var presenter: CityListPresenterImpl

    override fun onStart() {
        super.onStart()
        initLogicItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.city_list)
        initViews()

    }

    override fun getStringFromID(stringID: Int): String
            = getString(stringID)

    override fun showToast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun startForecastActivity() {
        TODO("Not yet implemented")
    }

    override fun startAddCityActivity() {
        val intent = Intent(this, AddCityActivity::class.java)
        startActivity(intent)
    }

    override fun getRoomDbDao(): CityDAO
            = CityRoomDB.getDatabase(this).getCityDao()

    override fun initWeathersList(citiesWeather: ArrayList<DayWeather>) {
        val adapter =CityListRvAdapter(citiesWeather, this)
        rvDayWeather.adapter = adapter
    }

    override fun addRvDayWeatherItem(weather: DayWeather) {
        (rvDayWeather.adapter as CityListRvAdapter).addItem(weather)
    }

    override fun removeRvDayWeatherItem(position: Int) {
        (rvDayWeather.adapter as CityListRvAdapter).removeItem(position)
    }

    override fun initViews() {
        content = findViewById(R.id.content_city_list)
        progress = findViewById(R.id.progressbar)

        rvDayWeather = findViewById(R.id.rv_city_list)
        rvDayWeather.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)

        fabCallAddCity = findViewById(R.id.fab_city_list)
    }

    override fun initLogicItem() {
        presenter = CityListPresenterImpl()
        presenter.initV(this)
        presenter.onCreateView()
    }

    override fun showContent() {
        content.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        content.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    override fun onClick(cityName: String) {
        presenter.onItemClick(cityName)
    }

    override fun onLongCLick(cityName: String, position: Int) {
        presenter.onItemLongClick(cityName, position)
    }
}