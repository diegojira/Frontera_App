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
import com.cicipn.geointeligencia_anp_app.other.Constants
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
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
        requireActivity().tvToolbarTitle.text = "Tu opinión"
        //Botón Sección Bienvenido
        btnSeccionBienvenido.setOnClickListener{
                val intent = Intent(activity, Sec1::class.java)
                startActivity(intent)
        }

        //Botón Sección Servicios
        btnSeccionServicios.setOnClickListener{
            Toast.makeText(context, "¡Sección en construcción!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, Sec2::class.java)
            startActivity(intent)
        }

        //Botón Sección Regrese pronto
        btnSeccionRegresa.setOnClickListener{
            //Toast.makeText(context, "¡Sección en construcción!", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, Sec3::class.java)
            startActivity(intent)
        }

        btnEnviarEnc.setOnClickListener{
            //Revisión de conexión a Internet
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            //Revisión si ya se ha contestado la encuesta
            val isSec1Answered = sharedPref.getBoolean(Constants.KEY_SEND1,false)
            //Ya que estén implementadas las secciones 2 y 3, descomentar las siguientes dos líneas
            //val isSec2Answered = sharedPref.getBoolean(Constants.KEY_SEND2,false)
            //val isSecAnswered = sharedPref.getBoolean(Constants.KEY_SEND2,false)

            if (isConnected && isSec1Answered){
                val data = StringBuilder()
                data.append("ID,Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8,Q9,Q10\n")
                val admin = AdminSQLiteOpenHelper(requireActivity(),"Encuesta",null,1)
                val db = admin.readableDatabase
                val result = db.rawQuery("SELECT * FROM RESPUESTAS",null)
                if (result.moveToFirst()){
                    //Toast.makeText(context,"Conectado a Internet "+result.columnCount.toString(),Toast.LENGTH_SHORT).show()
                        for (i in 0..result.columnCount-1) {
                            data.append(result.getString(i))
                            data.append(",")
                        }
                }
                try {
                    val out: FileOutputStream = requireContext().openFileOutput("encuesta_geointeligencia.csv", Context.MODE_PRIVATE)
                    out.write((data.toString()).toByteArray())
                    out.close()
                    val fileLocation = File(activity?.filesDir,"encuesta_geointeligencia.csv")
                    val path: Uri = FileProvider.getUriForFile(requireContext(),"com.example.geointeligencia_ANP_App.FileProvider",fileLocation)
                    val sendIntent = Intent(Intent.ACTION_SEND)
                    sendIntent.data = Uri.parse("mailto:")
                    sendIntent.setType("text/csv")
                    //Falta agregar el destinatario.
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("gisfrontera@gmail.com"))
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Encuesta contestada " +
                            "por ${sharedPref.getString(KEY_NAME,".")}")
                    sendIntent.putExtra(Intent.EXTRA_TEXT,"Enviado desde Geointeligencia App")
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    sendIntent.putExtra(Intent.EXTRA_STREAM,path)
                    startActivity(Intent.createChooser(sendIntent,"Send mail"))

                } catch(e: Exception){

                }
            }

            else if (isConnected && !isSec1Answered) Toast.makeText(context,"Conteste primero la encuesta",Toast.LENGTH_SHORT).show()
            else Toast.makeText(context,"Sin conexión. Intente más tarde.",Toast.LENGTH_SHORT).show()

        }
    }


}