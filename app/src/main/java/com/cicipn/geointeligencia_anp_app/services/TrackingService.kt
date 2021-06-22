package com.cicipn.geointeligencia_anp_app.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_PAUSE_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.ACTION_STOP_SERVICE
import com.cicipn.geointeligencia_anp_app.other.Constants.FASTEST_LOCATION_INTERVAL
import com.cicipn.geointeligencia_anp_app.other.Constants.LOCATION_UPDATE_INTERVAL
import com.cicipn.geointeligencia_anp_app.other.Constants.NOTIFICATION_CHANNEL_ID
import com.cicipn.geointeligencia_anp_app.other.Constants.NOTIFICATION_CHANNEL_NAME
import com.cicipn.geointeligencia_anp_app.other.Constants.NOTIFICATION_ID
import com.cicipn.geointeligencia_anp_app.other.Constants.TIMER_UPDATE_INTERVAL
import com.cicipn.geointeligencia_anp_app.other.TrackingUtility
import com.cicipn.geointeligencia_anp_app.ui.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

typealias Polyline = MutableList<LatLng>
typealias PolyLines = MutableList<Polyline>
// we need Lifecycle Service to pass  an instance to the observe function
@AndroidEntryPoint
class TrackingService : LifecycleService() {

    var isFirstRoute = true
    var serviceKilled = false

    @Inject
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val timeRouteInSeconds = MutableLiveData<Long>()

    @Inject
    lateinit var baseNotificationBuilder: NotificationCompat.Builder

    lateinit var curNotificationBuilder: NotificationCompat.Builder

    // React to changes of position
    companion object {
        val timeRouteInMilis = MutableLiveData<Long>()
        // Save the list of several coordinates
        val isTracking = MutableLiveData<Boolean>()
        // use a list of Polylines
        val pathPoints = MutableLiveData<PolyLines>()
    }

    private fun postInitialValues() {
        isTracking.postValue(false)
        pathPoints.postValue(mutableListOf())
        timeRouteInSeconds.postValue(0L)
        timeRouteInMilis.postValue(0L)
    }

    override fun onCreate() {
        super.onCreate()
        curNotificationBuilder = baseNotificationBuilder
        postInitialValues()
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        isTracking.observe(this, Observer {
            updateLocationTracking(it)
            updateNotificationTrackingState(it)
        })
    }

    private fun killService() {
        serviceKilled = true
        isFirstRoute = true
        pauseService()
        postInitialValues()
        stopForeground(true)
        stopSelf()
    }

    // This function gets called whenever send a command to our service
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let{
            when(it.action){
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRoute) {
                        startForegroundService()
                        isFirstRoute = false
                    } else {
                        Timber.d("Resuming service")
                        startTimer()
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("Paused service")
                    pauseService()
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("Stopped service")
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private var isTimerEnabled = false
    private var lapTime = 0L
    private var timeRoute = 0L
    private var timeStarted = 0L
    private var lastSecondTimeStamp = 0L

    private fun startTimer() {
        addEmptyPolyline()
        isTracking.postValue(true)
        timeStarted = System.currentTimeMillis()
        isTimerEnabled = true

        CoroutineScope(Dispatchers.Main).launch {
            while (isTracking.value!!) {
                // time difference between now and timeStarted
                lapTime = System.currentTimeMillis() - timeStarted
                // post the new lapTime
                timeRouteInMilis.postValue(timeRoute + lapTime)
                if(timeRouteInMilis.value!! >= lastSecondTimeStamp + 1000L) {
                    timeRouteInSeconds.postValue(timeRouteInSeconds.value!! + 1)
                    lastSecondTimeStamp += 1000L
                }
                delay(TIMER_UPDATE_INTERVAL)
            }
            timeRoute += lapTime
        }
    }

    private fun pauseService() {
        isTracking.postValue(false)
        isTimerEnabled = false
    }

    private fun updateNotificationTrackingState(isTracking: Boolean) {
        val notificationActionText = if(isTracking) "Pausar" else "Continuar"
        val pendingIntent = if(isTracking) {
            val  pauseIntent = Intent(this, TrackingService::class.java).apply {
                action = ACTION_PAUSE_SERVICE
            }
            PendingIntent.getService(this, 1, pauseIntent, FLAG_UPDATE_CURRENT)
        } else {
            val resumeIntent = Intent(this,TrackingService::class.java).apply {
                action = ACTION_START_OR_RESUME_SERVICE
            }
            PendingIntent.getService(this, 2, resumeIntent, FLAG_UPDATE_CURRENT)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        curNotificationBuilder.javaClass.getDeclaredField("mActions").apply {
            isAccessible = true
            set(curNotificationBuilder, ArrayList<NotificationCompat.Action>())
        }

        if(!serviceKilled) {
            curNotificationBuilder = baseNotificationBuilder
                    .addAction(R.drawable.ic_pause_black_24dp, notificationActionText, pendingIntent)
            notificationManager.notify(NOTIFICATION_ID, curNotificationBuilder.build())
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking(isTracking: Boolean) {
        if(isTracking) {
            if (TrackingUtility.hasLocationPermission(this)){
                val request = LocationRequest().apply {
                    interval = LOCATION_UPDATE_INTERVAL
                    fastestInterval = FASTEST_LOCATION_INTERVAL
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                        request,
                        locationCallback,
                        Looper.getMainLooper()
                )
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            if(isTracking.value!!) {
                result?.locations?.let{
                    locations ->
                    for(location in locations) {
                        addPathPoint(location)
                        Timber.d("New location: ${location.latitude}, ${location.longitude}")
                    }
                }
            }
        }
    }

    private fun addPathPoint(location: Location?){
        // Check if the location isn't null
        location?.let{
            val pos = LatLng(location.latitude, location.longitude)
            // add this position to the polyline
            pathPoints.value?.apply {
                last().add(pos)
                pathPoints.postValue(this)
            }
        }
    }

    private fun addEmptyPolyline() = pathPoints.value?.apply {
        add(mutableListOf())
        pathPoints.postValue(this)
        // Initialize the list if the value is null, and the initial polyline is an empty list
    } ?: pathPoints.postValue(mutableListOf(mutableListOf()))

    private fun startForegroundService() {
        startTimer()

        isTracking.postValue(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }



        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())

        timeRouteInSeconds.observe(this, Observer {
            if(!serviceKilled) {
                val notification = baseNotificationBuilder
                        .setContentText(TrackingUtility.getFormattedStopWatchTime(it * 1000L, false))
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
        })
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        // It needs an ID for the channel, name and importance, The importance is set on low because we don't need that the phone sounds
        val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID,  NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }
}

