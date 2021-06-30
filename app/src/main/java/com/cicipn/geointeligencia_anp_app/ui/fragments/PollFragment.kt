    package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_poll.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.net.URI
import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import javax.inject.Inject

@AndroidEntryPoint
class PollFragment: Fragment(R.layout.fragment_poll){
    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            //Revisión de conexión a Internet
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected){
                val data = StringBuilder()
                data.append("ID,Q1,Q2,Q3")
                data.append("\nDiego,1,3,5")
                try {
                    val out: FileOutputStream = requireContext().openFileOutput("encuesta_geointeligencia.csv", Context.MODE_PRIVATE)
                    out.write((data.toString()).toByteArray())
                    out.close()
                    val fileLocation = File(activity?.filesDir,"encuesta_geointeligencia.csv")
                    val path: Uri = FileProvider.getUriForFile(requireContext(),"com.example.geointeligencia_ANP_App.FileProvider",fileLocation)
                    val sendIntent = Intent(Intent.ACTION_SEND)
                    sendIntent.setType("text/csv")
                    sendIntent.putExtra(Intent.EXTRA_EMAIL,"prueba@hotmail.com")
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "GeoInteligencia. Encuesta contestada " +
                            "por ${sharedPref.getString(KEY_NAME,".")}")
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Enviado desde Geointeligencia App")
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    sendIntent.putExtra(Intent.EXTRA_STREAM,path)
                    startActivity(Intent.createChooser(sendIntent,"Send mail"))

                } catch(e: Exception){

                }
                Toast.makeText(context,"Conectado a Internet",Toast.LENGTH_SHORT).show()
            }

            else Toast.makeText(context,"Desconectado a Internet",Toast.LENGTH_SHORT).show()



            //val uri = Uri.fromFile()
            //Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
        }
    }


}