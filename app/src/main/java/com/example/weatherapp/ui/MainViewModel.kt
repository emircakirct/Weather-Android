package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.RemoteDataSource
import com.example.weatherapp.data.WeatherAPI
import com.example.weatherapp.data.response.WeatherResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val weatherApi = RemoteDataSource().buildApi(WeatherAPI::class.java)
    private val _forecastsResponse = MutableLiveData<List<WeatherResponse>>()
    val forecastsResponse = _forecastsResponse
    private val _weatherResponse = MutableLiveData<WeatherResponse>()
    val weatherResponse = _weatherResponse
    private lateinit var _tempUnit: String
    private var _days: Int = 0
    private lateinit var _city: String
    val tempUnit
    get() = _tempUnit
    val days
    get() = _days
    val city
    get() = _city

    fun getWeatherForecast() {
        viewModelScope.launch {
            val response = weatherApi.getForecasts(city, days,
                BuildConfig.API_KEY,tempUnit)
            _forecastsResponse.value = response.list
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            val response = weatherApi.getCurrent(city,
                BuildConfig.API_KEY,tempUnit)
            _weatherResponse.value = response
        }
    }

    fun updateSettings(city: String, days: Int, isFahrenheit: Boolean) {
        if (isFahrenheit)
            _tempUnit = "imperial"
        else
            _tempUnit = "metric"

        _city = city

        _days = days
    }
}