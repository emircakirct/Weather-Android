package com.example.weatherapp.ui.forecasts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.data.response.WeatherResponse
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ForecastsAdapter(private val dataSet: List<WeatherResponse>, private val tempUnit: String)
                    : RecyclerView.Adapter<ForecastsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val description: TextView
        val img: ImageView
        val minMax: TextView

        init {
            // Define click listener for the ViewHolder's View.
            title = view.findViewById(R.id.txtDate)
            description = view.findViewById(R.id.txtDescription)
            img = view.findViewById(R.id.imgWeather)
            minMax = view.findViewById(R.id.txtMinMix)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_weather_daily, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val date = SimpleDateFormat("dd MMM yyyy",
            Locale.ENGLISH).format(Date(dataSet[position].dt.toLong()*1000))
        viewHolder.title.text = date
        viewHolder.description.text = dataSet[position].weather.first().description
        val unit = if (tempUnit == "imperial") "°F" else "°C"
        val minMax = "${dataSet[position].temp.min}$unit / ${dataSet[position].temp.max}$unit"
        viewHolder.minMax.text = minMax
        val url = "https://openweathermap.org/img/wn/${dataSet[position].weather.first().icon}@4x.png"
        Picasso.get().load(url)
            .placeholder(R.drawable.ic_weather)
            .error(R.drawable.ic_weather)
            .into(viewHolder.img)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}