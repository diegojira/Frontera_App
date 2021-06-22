package com.cicipn.geointeligencia_anp_app.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cicipn.geointeligencia_anp_app.db.Route
import com.cicipn.geointeligencia_anp_app.repositories.MainRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
        val mainRepository: MainRepository
): ViewModel(){

    val routeSortedByDate = mainRepository.getAllRoutesSortedByDate()

    fun insertRoute(route: Route) = viewModelScope.launch {
        mainRepository.insertRoute(route)
    }
}