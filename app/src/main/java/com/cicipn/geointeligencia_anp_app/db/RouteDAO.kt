package com.cicipn.geointeligencia_anp_app.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RouteDAO {
    //Create the querys to get the data that we need
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: Route)

    @Delete
    suspend fun deleteRoute(route: Route)

    @Query("SELECT * FROM tracking_table ORDER BY timestamp DESC")
    fun getAllRoutesSortedByDate(): LiveData<List<Route>>

    @Query("SELECT * FROM tracking_table ORDER BY distance DESC")
    fun getAllRoutesSortedByDistance(): LiveData<List<Route>>

    @Query("SELECT * FROM tracking_table ORDER BY timeInMillis DESC")
    fun getAllRouteSortedByTimeInMillis(): LiveData<List<Route>>

    @Query("SELECT SUM(timeInMillis) FROM tracking_table")
    fun getTotalTimeInMillis(): LiveData<Long>

    @Query("SELECT SUM(distance) FROM tracking_table")
    fun getTotalDistance(): LiveData<Long>
}