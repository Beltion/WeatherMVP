package com.example.weathermvp.presentation.addcity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.weathermvp.R
import com.example.weathermvp.business.AddCityView
import com.example.weathermvp.framework.room.CityDAO
import com.example.weathermvp.framework.room.CityRoomDB
import com.example.weathermvp.presentation.citylist.CityListActivity

class AddCityActivity :
        AppCompatActivity(),
        AddCityView{

    private lateinit var content: ConstraintLayout
    private lateinit var progress: ProgressBar
    private lateinit var cityName: EditText
    private lateinit var getCityBtn: Button
    private lateinit var cancelAdd: Button

    private lateinit var presenter: AddCityPresenterImpl

    override fun onStart() {
        super.onStart()
        initLogicItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_city)
        initViews()
    }

    override fun getCityName(): String
            = cityName.text.toString()

    override fun getStringFromID(stringID: Int): String
            = getString(stringID)

    override fun showToast(str: String) {
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }

    override fun startCityListActivity() {
        val intent = Intent(this, CityListActivity::class.java)
        startActivity(intent)
    }

    override fun getRoomDbDao(): CityDAO
            = CityRoomDB.getDatabase(this).getCityDao()

    override fun initViews() {
        content = findViewById(R.id.content_add_city)
        progress = findViewById(R.id.progressbar)

        cityName = findViewById(R.id.city_et_add_city)
        getCityBtn = findViewById(R.id.get_city_btn_add_city)
        cancelAdd = findViewById(R.id.cancel_city_btn_add_city)

        presenter = AddCityPresenterImpl()
        presenter.initV(this)
    }

    override fun initLogicItem() {
        presenter.onCreateView()
        getCityBtn.setOnClickListener {
            presenter.onBtnClick()
        }
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