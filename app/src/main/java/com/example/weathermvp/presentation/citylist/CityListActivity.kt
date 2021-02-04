package com.example.weathermvp.presentation.citylist

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.example.weathermvp.business.CityListView
import com.example.weathermvp.framework.room.CityDAO
import com.example.weathermvp.framework.room.CityRoomDB
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CityListActivity:
        CityListView,
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
        initViews()
        setContentView(R.layout.city_list)
    }

    override fun getStringFromID(stringID: Int): String
            = getString(stringID)

    override fun showToast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun startForecastActivity() {
        TODO("Not yet implemented")
    }

    override fun getRoomDbDao(): CityDAO
            = CityRoomDB.getDatabase(this).getCityDao()

    override fun initViews() {
        content = findViewById(R.id.content_city_list)
        progress = findViewById(R.id.progressbar)

        rvDayWeather = findViewById(R.id.rv_city_list)
        fabCallAddCity = findViewById(R.id.fab_city_list)
    }

    override fun initLogicItem() {
        presenter = CityListPresenterImpl()
        presenter.initV(this)
    }

    override fun showContent() {
        content.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

    override fun hideContent() {
        content.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }
}