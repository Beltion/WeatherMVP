package com.example.weathermvp.presentation.forecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weathermvp.R
import com.google.android.material.textview.MaterialTextView

class ForecastDaysRvAdapter(
        private val days: ArrayList<String>,
        private val listener: OnForecastDaysClickListener
): RecyclerView.Adapter<ForecastDaysRvAdapter.ForecastDayVH>() {
    class ForecastDayVH(itemView: View): RecyclerView.ViewHolder(itemView){
        val day = itemView.findViewById<MaterialTextView>(R.id.day_tv_forecast_day_item)

        fun onDayClick(position: Int, clickListener: OnForecastDaysClickListener){
            itemView.setOnClickListener {
                clickListener.onDayClick(position)
            }
        }
    }

    interface OnForecastDaysClickListener{
        fun onDayClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastDayVH {
        val ctx = parent.context
        val inflater = LayoutInflater.from(ctx)
        val card = inflater.inflate(R.layout.forecast_day_item, parent, false)
        return ForecastDayVH(card)
    }

    override fun onBindViewHolder(holder: ForecastDayVH, position: Int) {
        holder.day.text = days[position]
        holder.onDayClick(position, listener)
    }

    override fun getItemCount() = days.size
}