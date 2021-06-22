package com.cicipn.geointeligencia_anp_app.repositories

import com.cicipn.geointeligencia_anp_app.db.RouteDAO
import com.cicipn.geointeligencia_anp_app.db.Route
import javax.inject.Inject

//This repository is in charge of provide the functions of the routeDAO
class MainRepository @Inject constructor(
    val routeDAO: RouteDAO
){
    suspend fun insertRoute(route: Route ) = routeDAO.insertRoute(route)

    suspend fun deleteRoute(route: Route ) = routeDAO.deleteRoute(route)

    fun getAllRoutesSortedByTimeInMillis() = routeDAO.getAllRouteSortedByTimeInMillis()

    fun getAllRoutesSortedByDate() = routeDAO.getAllRoutesSortedByDate()

    fun getAllRoutesSortedByDistance() = routeDAO.getAllRoutesSortedByDistance()

    fun getTotalDistance() = routeDAO.getTotalDistance()

    fun getTotelTimeInMillis() = routeDAO.getTotalTimeInMillis()
}