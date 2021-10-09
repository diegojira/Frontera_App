package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cicipn.geointeligencia_anp_app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.Polyline
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*

@AndroidEntryPoint
class MapFragment: Fragment(R.layout.fragment_map){

    private lateinit var osmMap: MapView
    private lateinit var locationOverlay: MyLocationNewOverlay
    private var previousLat = 0.0;
    private var previousLong = 0.0
    private var newLat = 0.0;
    private var newLong = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_map, container, false)
        osmMap = root.findViewById(R.id.mapViewMapSection)
        setMap()
        // Titulo del fragmento
        requireActivity().tvToolbarTitle.text = "Mapa del área"
        return root
    }

    private fun setMap(){
        osmMap.tileProvider.tileSource = TileSourceFactory.MAPNIK
        osmMap.setMultiTouchControls(true)

        val ctx = requireActivity().applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        val controller: MapController = osmMap.controller as MapController
        controller.setZoom(18)

        locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), osmMap)
        this.locationOverlay.enableMyLocation()
        locationOverlay.runOnFirstFix( object: Runnable {
            override fun run() {
                requireActivity().runOnUiThread( object: Runnable {
                    override fun run() {
                        val geoPoint = GeoPoint(locationOverlay.myLocation)
                        controller.animateTo(locationOverlay.myLocation)
                        controller.setCenter(geoPoint)
                    }
                })
            }
        })

        osmMap.overlays.add(locationOverlay)

    /*
        previousLat = 18.189538;
        previousLong = -97.247640

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                //Draw a line every time to the map, this simulates to draw the route of the user
                val geoPoints = ArrayList<GeoPoint>();
                newLat = previousLat + 0.0001
                newLong = previousLong + 0.0001
                val polyLine = Polyline();    //Create an empty polyline
                geoPoints.add(GeoPoint(previousLat, previousLong))   //Add previous point
                geoPoints.add(GeoPoint(newLat, newLong))   //Add new point
                polyLine.setPoints(geoPoints);  //Add the points to the polyline
                osmMap.overlays.add(polyLine); //Add the polyline to the map
                osmMap.invalidate();    //In order to show the new line inmediatly
                previousLat = newLat    //New values are going to be the previous in the next loop
                previousLong = newLong
            }
        }, 0, 5000) //put here time 1000 milliseconds=1 second
*/
    }

}