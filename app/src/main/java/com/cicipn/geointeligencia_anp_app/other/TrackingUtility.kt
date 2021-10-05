package com.cicipn.geointeligencia_anp_app.other

import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest
import android.location.Location
import com.cicipn.geointeligencia_anp_app.services.Polyline
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.geojson.GeoJsonFeature
import com.google.maps.android.data.geojson.GeoJsonLineString
import org.osmdroid.util.GeoPoint
import timber.log.Timber
import java.util.concurrent.TimeUnit

object TrackingUtility {
    fun hasLocationPermission(context: Context) =
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.hasPermissions(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } else {
                EasyPermissions.hasPermissions(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                )
            }

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distance = 0f
        for(i in 0..polyline.size - 2) {
            val pos1 = polyline[i]
            val pos2 = polyline[i + 1]

            val result = FloatArray(1)

            Location.distanceBetween(
                    pos1.latitude,
                    pos1.longitude,
                    pos2.latitude,
                    pos2.longitude,
                    result
            )
            distance += result[0]
        }
        System.out.println("La distancia calculada: " + distance)
        return distance
    }

    fun createGeoJson(polyline: Polyline): MutableList<GeoPoint> {
        val lineStringArray: MutableList<GeoPoint> = ArrayList()
        for(i in 0 until polyline.size) {
            lineStringArray.add(polyline[i])
        }
        Timber.d("polyline:  ${polyline}")
        Timber.d("lineStringArray:  ${lineStringArray}")
        // val lineString = GeoJsonLineString(lineStringArray)
        // return GeoJsonFeature(lineString, null, null, null)
        return lineStringArray
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis:Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        var minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MILLISECONDS.toSeconds(minutes)
        var seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)


        if(!includeMillis) {
            return "${if(hours < 10) "0" else ""}$hours:" +
                    "${if(minutes < 10) "0" else ""}$minutes:" +
                    "${if(seconds < 10) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= 10

        seconds = seconds % 60
        minutes = minutes % 60

        return "${if(hours < 10) "0" else ""}$hours:" +
                "${if(minutes < 10) "0" else ""}$minutes:" +
                "${if(seconds < 10) "0" else ""}$seconds:" +
                "${if(milliseconds < 10) "0" else ""}$milliseconds"

    }
}