package com.example.weatherapp.data

import com.example.weatherapp.data.response.DailyResponse
import com.example.weatherapp.data.response.WeatherResponse
import retrofit2.http.*

interface WeatherAPI {

    @GET("/data/2.5/weather")
    suspend fun getCurrent(
        @Query("q") city: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): WeatherResponse

    @GET("/data/2.5/forecast/daily")
    suspend fun getForecasts(
        @Query("q") city: String,
        @Query("cnt") daysCount: Int = 7,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): DailyResponse
}