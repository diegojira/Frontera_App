package com.cicipn.geointeligencia_anp_app.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.cicipn.geointeligencia_anp_app.repositories.MainRepository

class ShareViewModel @ViewModelInject constructor(
        val mainRepository: MainRepository 
): ViewModel(){

        val totalTimeRoute = mainRepository.getTotelTimeInMillis()
        val totalDistance = mainRepository.getTotalDistance()
}