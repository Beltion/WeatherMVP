package com.example.weathermvp.presentation.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.example.weathermvp.data.entities.DayWeather
import com.squareup.picasso.Picasso

class CityListRvAdapter(
        private val weathers: ArrayList<DayWeather>,
        private val clickListener: OnCityListClickListener
): RecyclerView.Adapter<CityListRvAdapter.CityListVH>()  {

    class CityListVH(item: View): RecyclerView.ViewHolder(item){
        val name = itemView.findViewById<TextView>(R.id.name_city_list_item)
        val desc = itemView.findViewById<TextView>(R.id.desc_city_list_item)
        val icon = itemView.findViewById<ImageView>(R.id.img_city_list_item)
        val temp = itemView.findViewById<TextView>(R.id.temp_city_list_item)
        val pressure = itemView.findViewById<TextView>(R.id.pressure_city_list_item)
        val humidity = itemView.findViewById<TextView>(R.id.humidity_city_list_item)
        val windSpeed = itemView.findViewById<TextView>(R.id.wind_speed_city_list_item)
        val clouds = itemView.findViewById<TextView>(R.id.clouds_city_list_item)

        fun onClick(cityName: String, listener: OnCityListClickListener){
            itemView.setOnClickListener{
                listener.onClick(cityName)
            }
        }

        fun onLongClick(cityName: String, listener: OnCityListClickListener){
            itemView.setOnLongClickListener{
                listener.onLongCLick(cityName)
                true
            }
        }
    }

    interface OnCityListClickListener{
        fun onClick(cityName: String)
        fun onLongCLick(cityName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListVH {
        val ctx = parent.context
        val inflater = LayoutInflater.from(ctx)
        val card = inflater.inflate(R.layout.city_list_item, parent, false)
        return CityListVH(card)
    }

    override fun onBindViewHolder(holder: CityListVH, position: Int) {
        val desc = weathers[position].weather[0].description
        val name = weathers[position].name
        val icon = weathers[position].weather[0].icon
        val temp = "${weathers[position].main.temp} ℃"
        val pressure = "давление ${weathers[position].main.pressure}"
        val humidity = "влажность ${weathers[position].main.humidity}"
        val windSpeed = "скорость ветра ${weathers[position].wind}"
        val clouds = "облачность ${weathers[position].clouds}"

        holder.name.text = name
        holder.desc.text = desc
        holder.temp.text = temp
        holder.pressure.text = pressure
        holder.humidity.text = humidity
        holder.windSpeed.text = windSpeed
        holder.clouds.text = clouds

        Picasso.get()
                .load("http://openweathermap.org/img/wn/$icon@2x.png")
                .fit()
                .into(holder.icon)

        holder.onClick(weathers[position].name, clickListener)
        holder.onLongClick(weathers[position].name, clickListener)
    }

    override fun getItemCount(): Int = weathers.size

}