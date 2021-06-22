package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cicipn.geointeligencia_anp_app.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_poll.*

@AndroidEntryPoint
class PollFragment: Fragment(R.layout.fragment_poll){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //btnSubmitPoll.setOnClickListener{
          //  findNavController().navigate(R.id.action_pollFragment_to_routeFragment)
        //}

        //Botón Sección Bienvenido
        btnSeccionBienvenido.setOnClickListener{
                val intent = Intent(activity, Sec1::class.java)
                startActivity(intent)
        }

        //Botón Sección Servicios
        btnSeccionServicios.setOnClickListener{
            val intent = Intent(activity, Sec2::class.java)
            startActivity(intent)
        }

        //Botón Sección Regrese pronto
        btnSeccionRegresa.setOnClickListener{
            val intent = Intent(activity, Sec3::class.java)
            startActivity(intent)
        }
    }


}