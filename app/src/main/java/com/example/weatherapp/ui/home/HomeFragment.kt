package com.example.weatherapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.response.WeatherResponse
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.ui.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val mainViewModel: MainViewModel by activityViewModels()
    private val sharedPreferences by lazy { requireActivity().getSharedPreferences(
        getString(R.string.preference_file_key), Context.MODE_PRIVATE) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Showing the ProgressBar, Making the main design GONE
        binding!!.loader.visibility = View.VISIBLE

        //load settings
        loadSettings()

        return binding!!.root
    }

    private fun updateUI(weather: WeatherResponse) {
        val updatedAt = weather.dt.toLong()
        val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a",
                                                            Locale.ENGLISH).format(Date(updatedAt*1000))
        val unit = if (mainViewModel.tempUnit.equals("imperial")) "°F" else "°C"
        //Populating extracted data into our views
        binding?.let { binding ->
            binding.address.text = "${weather.name}, ${weather.sys.country}"
            binding.updatedAt.text = updatedAtText
            binding.status.text = weather.weather.first().description
                                                    .replaceFirstChar(Char::titlecase)
            binding.temp.text = "${weather.main.temp.toInt()}$unit"
            binding.tempMin.text = "Min Temp: ${weather.main.tempMin}$unit"
            binding.tempMax.text = "Max Temp: ${weather.main.tempMax}$unit"
            binding.sunrise.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                                .format(Date(weather.sys.sunrise*1000))
            binding.sunset.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                                .format(Date(weather.sys.sunset*1000))
            binding.wind.text = String.format("%.2f",weather.wind.speed)
            binding.pressure.text = weather.main.pressure.toString()
            binding.humidity.text = weather.main.humidity.toString()

            binding?.let {binding ->
                //Showing the ProgressBar, Making the main design GONE
                binding.loader.visibility = View.GONE
                binding.mainContainer.visibility = View.VISIBLE
            }
        }
}

    private fun loadSettings() {
        val city =  sharedPreferences.getString("city", "New York")!!
        val isFahrenheit = sharedPreferences.getBoolean("fahrenheit", true)
        val days = sharedPreferences.getInt("days", 1) + 6

        mainViewModel.updateSettings(city,days,isFahrenheit)
        mainViewModel.getCurrentWeather()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.weatherResponse.observe(viewLifecycleOwner) { weather ->
            updateUI(weather)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}