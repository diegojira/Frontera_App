package com.cicipn.geointeligencia_anp_app.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.cicipn.geointeligencia_anp_app.repositories.MainRepository

class TrackingViewModel @ViewModelInject constructor(
        val mainRepository: MainRepository 
): ViewModel(){

}