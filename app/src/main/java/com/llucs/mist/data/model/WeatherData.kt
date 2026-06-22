package com.llucs.mist.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val current: CurrentWeather,
    val daily: DailyWeather,
    val timezone: String
)

data class CurrentWeather(
    val time: String,
    @SerializedName("temperature_2m") val temperature: Double,
    @SerializedName("relative_humidity_2m") val humidity: Double,
    @SerializedName("apparent_temperature") val feelsLike: Double,
    @SerializedName("weather_code") val weatherCode: Int,
    @SerializedName("wind_speed_10m") val windSpeed: Double,
    @SerializedName("uv_index") val uvIndex: Double
)

data class DailyWeather(
    val time: List<String>,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>,
    @SerializedName("weather_code") val weatherCode: List<Int>,
    @SerializedName("precipitation_probability_max") val precipitationProbability: List<Int>?,
    val sunrise: List<String>?,
    val sunset: List<String>?
)

data class GeocodingResponse(
    val results: List<GeocodingResult>?
)

data class GeocodingResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?,
    val admin1: String?,
    val country_code: String?
)

data class WeatherState(
    val temperature: String = "--",
    val feelsLike: String = "--",
    val humidity: String = "--",
    val windSpeed: String = "--",
    val uvIndex: String = "--",
    val weatherCode: Int = 0,
    val condition: String = "--",
    val locationName: String = "Set Location",
    val dailyForecast: List<DayForecast> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

data class DayForecast(
    val day: String,
    val date: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherCode: Int,
    val precipitationProbability: Int?
)
