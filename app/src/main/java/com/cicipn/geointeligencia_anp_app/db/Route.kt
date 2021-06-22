package com.cicipn.geointeligencia_anp_app.db

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.data.geojson.GeoJsonFeature

@Entity(tableName = "tracking_table")
data class Route(
    // Posible preview image, it needs be converted to data type that the Room can read
    // The TypeConverter in  the class
    //var img: Bitmap? = null,
    // Store the geojson
    // var route: GeoJsonFeature? = null,
    var coordinates: String = "",
    // For measure the date when the user make the trip
    var timestamp: Long = 0L,
    // For measure the distance of the trip the distance is on metters
    var distance: Int = 0,
    // For measure how long the trip was
    var timeInMillis: Long = 0L
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}