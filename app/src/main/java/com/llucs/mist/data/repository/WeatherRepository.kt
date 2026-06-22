package com.llucs.mist.data.repository

import com.llucs.mist.data.api.ApiClient
import com.llucs.mist.data.model.DayForecast
import com.llucs.mist.data.model.WeatherState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): WeatherState = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.weatherApi.getForecast(lat, lon)
            val current = response.current

            val daily = response.daily
            val forecasts = daily.time.mapIndexed { index, dateStr ->
                val date = LocalDate.parse(dateStr)
                val dayName = date.dayOfWeek.getDisplayName(
                    java.time.format.TextStyle.SHORT, Locale.ENGLISH
                )
                DayForecast(
                    day = if (index == 0) "Today" else dayName,
                    date = dateStr,
                    maxTemp = "${daily.temperatureMax[index].toInt()}°",
                    minTemp = "${daily.temperatureMin[index].toInt()}°",
                    weatherCode = daily.weatherCode[index],
                    precipitationProbability = daily.precipitationProbability?.get(index)
                )
            }

            WeatherState(
                temperature = "${current.temperature.toInt()}°",
                feelsLike = "${current.feelsLike.toInt()}°",
                humidity = "${current.humidity.toInt()}%",
                windSpeed = "${current.windSpeed.toInt()} km/h",
                uvIndex = current.uvIndex.toInt().toString(),
                weatherCode = current.weatherCode,
                condition = getConditionText(current.weatherCode),
                dailyForecast = forecasts,
                isLoading = false,
                error = null
            )
        } catch (e: Exception) {
            WeatherState(
                isLoading = false,
                error = e.message ?: "Failed to fetch weather"
            )
        }
    }

    private fun getConditionText(code: Int): String = when (code) {
        0 -> "Clear"
        in 1..3 -> "Partly Cloudy"
        45, 48 -> "Foggy"
        in 51..55 -> "Drizzle"
        in 56..57 -> "Freezing Drizzle"
        in 61..65 -> "Rain"
        in 66..67 -> "Freezing Rain"
        in 71..77 -> "Snow"
        in 80..82 -> "Rain Showers"
        in 85..86 -> "Snow Showers"
        95 -> "Thunderstorm"
        in 96..99 -> "Hailstorm"
        else -> "Unknown"
    }
}
