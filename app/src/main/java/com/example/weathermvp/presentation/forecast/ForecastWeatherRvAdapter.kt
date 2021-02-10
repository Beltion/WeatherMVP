package com.example.weathermvp.presentation.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.example.weathermvp.data.entities.ForecastListItem
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class ForecastWeatherRvAdapter(
        private val weathers: ArrayList<ForecastListItem>
): RecyclerView.Adapter<ForecastWeatherRvAdapter.ForecastWeatherVH>() {
    class ForecastWeatherVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val time = itemView.findViewById<MaterialTextView>(R.id.time_tv_forecast_weather_item)
        val icom = itemView.findViewById<ImageView>(R.id.weather_iv_forecast_weather_item)
        val desc = itemView.findViewById<MaterialTextView>(R.id.desc_tv_forecast_weather_item)
        val temp = itemView.findViewById<MaterialTextView>(R.id.temp_tv_forecast_weather_item)
        val feelsLike = itemView.findViewById<MaterialTextView>(R.id.fells_like_tv_forecast_weather_item)
    }

    fun reInitList(newListSet: ArrayList<ForecastListItem>){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastWeatherVH {
       val ctx = parent.context
        val inflater = LayoutInflater.from(ctx)
        val card = inflater.inflate(R.layout.forecast_weather_item, parent, false)
        return ForecastWeatherVH(card)
    }

    override fun onBindViewHolder(holder: ForecastWeatherVH, position: Int) {

        val tempStr = "температура ${weathers[position].main.temp} ℃"
        val fellsLikeStr = "ощущается ${weathers[position].main.temp} ℃"

        holder.time.text = weathers[position].dt_txt
        holder.desc.text = weathers[position].weather[0].description
        holder.temp.text = tempStr
        holder.feelsLike.text = fellsLikeStr

        val icon = weathers[position].weather[0].icon

        Picasso.get()
                .load("http://openweathermap.org/img/wn/$icon@2x.png")
                .fit()
                .into(holder.icom)
    }

    override fun getItemCount() = weathers.size
}