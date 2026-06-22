package com.llucs.mist.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object LocationUtils {
    data class LocationResult(val lat: Double, val lon: Double, val success: Boolean)

    fun hasPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun getCurrentLocation(context: Context): LocationResult {
        if (!hasPermission(context)) {
            return LocationResult(0.0, 0.0, false)
        }
        return suspendCancellableCoroutine { cont ->
            try {
                val fusedClient = LocationServices.getFusedLocationProviderClient(context)
                fusedClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        cont.resume(LocationResult(location.latitude, location.longitude, true))
                    } else {
                        cont.resume(LocationResult(0.0, 0.0, false))
                    }
                }.addOnFailureListener {
                    cont.resume(LocationResult(0.0, 0.0, false))
                }
            } catch (e: Exception) {
                cont.resume(LocationResult(0.0, 0.0, false))
            }
        }
    }
}
