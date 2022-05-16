package com.example.weatherapp.data.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("weather"    ) val weather    : ArrayList<Weather>,
    @SerializedName("main"       ) val main       : Main,
    @SerializedName("wind"       ) val wind       : Wind,
    @SerializedName("dt"         ) val dt         : Int,
    @SerializedName("temp"       ) val temp       : Temp,
    @SerializedName("sys"        ) val sys        : Syst,
    @SerializedName("name"       ) val name       : String
)

data class Main (
    @SerializedName("temp"       ) val temp      : Double,
    @SerializedName("feels_like" ) val feelsLike : Double,
    @SerializedName("temp_min"   ) val tempMin   : Double,
    @SerializedName("temp_max"   ) val tempMax   : Double,
    @SerializedName("pressure"   ) val pressure  : Int,
    @SerializedName("humidity"   ) val humidity  : Int
)

data class Syst (
    @SerializedName("country" ) val country : String,
    @SerializedName("sunrise" ) val sunrise : Long,
    @SerializedName("sunset"  ) val sunset  : Long

)

data class Temp (
    @SerializedName("min"   ) val min   : Double,
    @SerializedName("max"   ) val max   : Double
)

data class Weather (
    @SerializedName("main"        ) val main        : String,
    @SerializedName("description" ) val description : String,
    @SerializedName("icon"        ) val icon        : String
)

data class Wind (@SerializedName("speed" ) val speed : Double)
