package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.db.Route
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_PAUSE_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_STOP_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.POLYLINE_COLOR
import com.cicipn.geointeligencia_anp_app.other.Constants.POLYLINE_WIDTH
import com.cicipn.geointeligencia_anp_app.other.TrackingUtility
import com.cicipn.geointeligencia_anp_app.services.PolyLines
import com.cicipn.geointeligencia_anp_app.services.Polyline
import com.cicipn.geointeligencia_anp_app.services.TrackingService
import com.cicipn.geointeligencia_anp_app.ui.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.maps.android.data.geojson.GeoJsonFeature
import com.google.maps.android.data.geojson.GeoJsonLineString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tracking.*
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import timber.log.Timber
import java.util.*
import kotlin.math.round

const val CANCEL_TRACKING_DIALOG_TAG = "CancelDialog"

@AndroidEntryPoint
class TrackingFragment: Fragment(R.layout.fragment_tracking){
    private val viewModel: MainViewModel by viewModels()

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()
    // Map Open Street Maps
    private lateinit var osmMap: MapView
    private lateinit var myLocationOverlay: MyLocationNewOverlay
    private lateinit var controller: MapController

    private var curTimeMillis = 0L

    private var menu: Menu? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_tracking, container, false)
        osmMap = root.findViewById(R.id.mapView)
        // Titulo del fragmento
        requireActivity().tvToolbarTitle.text = "Registrar ruta"
        setMap()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnToggleRun.setOnClickListener(){
            toggleRoute()
        }

        btnFinishRun.setOnClickListener{
            //zoomToSeeWholeTrack()
            endRouteAndSaveToDb()
        }

        if(savedInstanceState != null) {
            val cancelTrackingDialog = parentFragmentManager.findFragmentByTag(
                CANCEL_TRACKING_DIALOG_TAG
            ) as CancelTrackingDialog?

            cancelTrackingDialog?.setYesListener {
                stopRoute()
            }
        }
        /*
        val polyline: Polyline = Polyline()
        val geoPoints: ArrayList<GeoPoint> = ArrayList()
        // add Gepoint to array here.
        // add Gepoint to array here.
        polyline.setPoints(geoPoints)
        polyline.setWidth(mywidth)
        polyline.setColor(mycolor)
        map.getOverlayManager().add(polyline)
        map.invalidate()
        osmMap.
        /*
        mapView.getMapAsyn {
            map = it
            addAllPolylines()
        }
        */

         */
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoints = it
            addLatestPolyline()
            // moveCameraToUser()
        })

        TrackingService.timeRouteInMilis.observe(viewLifecycleOwner, Observer {
            curTimeMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRoute(){
        if(isTracking) {
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    // Set the view for the route start and while route
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if(!isTracking && curTimeMillis > 0L) {
            btnToggleRun.text = "Continuar"
            btnFinishRun.visibility = View.VISIBLE
        } else if(isTracking){
            btnToggleRun.text = "Terminar"
            menu?.getItem(0)?.isVisible = true
            btnFinishRun.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu, menu)
        this.menu = menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        if(curTimeMillis > 0L) {
            this.menu?.getItem(0)?.isVisible = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miCancelTracking -> {
                showCancelTrackingDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCancelTrackingDialog() {
        CancelTrackingDialog().apply {
            setYesListener{
                stopRoute()
            }
        }.show(parentFragmentManager, CANCEL_TRACKING_DIALOG_TAG)
    }

    private fun stopRoute() {
        tvTimer.text = "00:00:00:00"
        sendCommandToService(ACTION_STOP_SERVICE)
        // Change made to add the poll fragment into the navigation graph
    // findNavController().navigate(R.id.action_trackingFragment_to_routeFragment)
        findNavController().navigate(R.id.action_trackingFragment_to_pollFragment)
    }

    /*
    private fun moveCameraToUser() {
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            pathPoints.last().last(),
                            MAP_ZOOM
                    )
            )
        }
    }


    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for(polyline in pathPoints) {
            for(pos in polyline){
                bounds.include(pos)
            }
        }

        map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                        bounds.build(),
                        mapView.width,
                        mapView.height,
                        (mapView.height * 0.0f.toInt())
                )
        )
    }
*/
    private fun endRouteAndSaveToDb() {
        // map?.snapshot{ bmp ->
            var distanceInMeters = 0
            for(polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            var listGeoJsonFeature: MutableList<GeoPoint> = ArrayList()
            // var listGeoJsonFeature: MutableList<MutableList<LatLng>>

            for(polyline in pathPoints) {
                // listGeoJsonFeature?.add(TrackingUtility.createGeoJson(polyline))
                listGeoJsonFeature = TrackingUtility.createGeoJson(polyline)
            }
            Timber.d("El tamaño de la lista: ${listGeoJsonFeature?.size}")

            //val lineString = GeoJsonLineString(listGeoJsonFeature)
            //val lineStringJSON = Gson().toJson(lineString)

            // val geoJsonRoute = GeoJsonFeature(lineString, null, null, null)


            //geoJsonRoute.
            // val avgSpeed = round ( (distanceInMeters / 1000f) / (curTimeMillis / 1000f / 60 / 60) * 10) / 10f
            //val dateTimestamp = Calendar.getInstance().timeInMillis
            // val route = Route(bmp,  dateTimestamp, distanceInMeters, curTimeMillis)
            //val route = Route(geoJsonRoute ,dateTimestamp, distanceInMeters, curTimeMillis)
            //val route = Route(lineStringJSON, dateTimestamp, distanceInMeters, curTimeMillis)
            //viewModel.insertRoute(route)
            Snackbar.make(
                    requireActivity().findViewById(R.id.rootView),
                    "Ruta guardada satisfactoriamente",
                    Snackbar.LENGTH_LONG
            ).show()
            stopRoute()
       // }
    }

    private fun addAllPolylines() {
        for(polyline in pathPoints) {
            val polyLine = org.osmdroid.views.overlay.Polyline();    //Create an empty polyline
            polyLine.setPoints(polyline)
            /*val polylineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .addAll(polyline)*/
            osmMap.overlays.add(polyLine); //Add the polyline to the map
            osmMap.invalidate();
            // map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val geoPoints = ArrayList<GeoPoint>()
            val polyLine = org.osmdroid.views.overlay.Polyline();    //Create an empty polyline
            geoPoints.add(preLastLatLng)
            geoPoints.add(lastLatLng)
            polyLine.setPoints(geoPoints)

            /*val polyLineOptions = PolylineOptions()
                    .color(POLYLINE_COLOR)
                    .width(POLYLINE_WIDTH)
                    .add(preLastLatLng)*/
            osmMap.overlays.add(polyLine)
            osmMap.invalidate();
                // Map?.addPolyLine(polyLineOptions)
        }
    }

    private fun sendCommandToService(action: String){
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume(){
        super.onResume()
        osmMap.onResume()
    }

    override fun onPause(){
        super.onPause()
        osmMap.onPause()
    }

    private fun setMap(){
        osmMap.tileProvider.tileSource = TileSourceFactory.MAPNIK
        osmMap.setMultiTouchControls(true)

        val ctx = requireActivity().applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID;
        controller = osmMap.controller as MapController

        //val geoPoint = GeoPoint(19.5020228, -99.0264017)


        //controller.setCenter(geoPoint)
        //controller.setZoom(18)

        myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireActivity().applicationContext), osmMap)
        myLocationOverlay.enableMyLocation()
        myLocationOverlay.enableFollowLocation()
        myLocationOverlay.runOnFirstFix{activity?.runOnUiThread {
            controller.animateTo(myLocationOverlay.myLocation)
            controller.setZoom(18)
            controller.setCenter(myLocationOverlay.myLocation)
        }}
        osmMap.overlays.add(myLocationOverlay)
    }
}