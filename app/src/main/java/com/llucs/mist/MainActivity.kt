package com.llucs.mist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.llucs.mist.data.model.WeatherState
import com.llucs.mist.data.repository.WeatherRepository
import com.llucs.mist.ui.screen.AboutScreen
import com.llucs.mist.ui.screen.SearchScreen
import com.llucs.mist.ui.screen.WeatherScreen
import com.llucs.mist.ui.theme.MistTheme
import com.llucs.mist.util.LocationUtils
import com.llucs.mist.util.PreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MistTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MistApp()
                }
            }
        }
    }
}

@Composable
fun MistApp() {
    var weatherState by remember { mutableStateOf(WeatherState()) }
    var screen by remember { mutableStateOf("weather") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val loadWeather: (Double, Double) -> Unit = { lat, lon ->
        scope.launch {
            weatherState = weatherState.copy(isLoading = true, error = null)
            weatherState = WeatherRepository.getWeather(lat, lon)
        }
    }

    val refresh: () -> Unit = {
        scope.launch {
            val lat = PreferencesManager.latitude.first()
            val lon = PreferencesManager.longitude.first()
            if (lat != 0.0 || lon != 0.0) {
                loadWeather(lat, lon)
            }
        }
    }

    LaunchedEffect(Unit) {
        val hasLoc = PreferencesManager.hasLocation()
        if (hasLoc) {
            refresh()
        } else {
            val result = LocationUtils.getCurrentLocation(context)
            if (result.success) {
                loadWeather(result.lat, result.lon)
                PreferencesManager.saveLocation("Current Location", result.lat, result.lon)
            }
        }
    }

    when (screen) {
        "weather" -> {
            Box(modifier = Modifier.fillMaxSize()) {
                WeatherScreen(
                    weatherState = weatherState,
                    onRefresh = { refresh() },
                    onSearchCity = { screen = "search" },
                    onRequestLocation = {
                        scope.launch {
                            val result = LocationUtils.getCurrentLocation(context)
                            if (result.success) {
                                loadWeather(result.lat, result.lon)
                                PreferencesManager.saveLocation(
                                    "Current Location", result.lat, result.lon
                                )
                            }
                        }
                    }
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    IconButton(onClick = { screen = "about" }) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                    }
                }
            }
        }
        "search" -> {
            SearchScreen(
                onBack = { screen = "weather" },
                onCitySelected = { name, lat, lon ->
                    scope.launch {
                        PreferencesManager.saveLocation(name, lat, lon)
                        loadWeather(lat, lon)
                    }
                    screen = "weather"
                }
            )
        }
        "about" -> {
            AboutScreen(onBack = { screen = "weather" })
        }
    }
}
