package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_poll.*
import java.net.URI
import javax.inject.Inject

@AndroidEntryPoint
class PollFragment: Fragment(R.layout.fragment_poll){
    @Inject
    lateinit var sharedPref: SharedPreferences

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

        btnEnviarEnc.setOnClickListener{
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "GeoInteligencia. Encuesta contestada " +
                    "por ${sharedPref.getString(KEY_NAME,"es chido")}")
            sendIntent.putExtra(Intent.EXTRA_TEXT,"Enviado desde Geointeligencia App")
            //val uri = Uri.fromFile()
            //Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
        }
    }


}