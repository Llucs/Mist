package com.llucs.mist.util

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "mist_prefs")

object PreferencesManager {
    private var appContext: Context? = null

    private val LAT_KEY = doublePreferencesKey("latitude")
    private val LON_KEY = doublePreferencesKey("longitude")
    private val LOCATION_KEY = stringPreferencesKey("location_name")

    private val ctx: Context get() = appContext ?: throw IllegalStateException("PreferencesManager not initialized")

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    val locationName: Flow<String> get() =
        ctx.dataStore.data.map { it[LOCATION_KEY] ?: "Set Location" }

    val latitude: Flow<Double> get() =
        ctx.dataStore.data.map { it[LAT_KEY] ?: 0.0 }

    val longitude: Flow<Double> get() =
        ctx.dataStore.data.map { it[LON_KEY] ?: 0.0 }

    suspend fun saveLocation(name: String, lat: Double, lon: Double) {
        ctx.dataStore.edit {
            it[LOCATION_KEY] = name
            it[LAT_KEY] = lat
            it[LON_KEY] = lon
        }
    }

    suspend fun hasLocation(): Boolean {
        return ctx.dataStore.data.first()[LAT_KEY] != null
    }
}
