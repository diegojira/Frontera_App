package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView

@AndroidEntryPoint
class MapFragment: Fragment(R.layout.fragment_map){

    private lateinit var osmMap: MapView

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
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID;
        val controller: MapController = osmMap.controller as MapController

        val geoPoint = GeoPoint(18.189538, -97.247640)
        controller.setCenter(geoPoint)
        controller.setZoom(14)
    }

}