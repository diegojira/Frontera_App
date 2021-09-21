package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.lang.Exception
import kotlin.math.log

class Sec2 : AppCompatActivity() {
//    lateinit var sharedPref: SharedPreferences
    lateinit var q3_lugar : Spinner //Spinner para los lugares de procedencia
    lateinit var enviarSec2 : Button // Boton que extrae respuestas
    lateinit var idsRG : Array<Int> // Arreglo donde se guardaran los ids e los radiogroups
    lateinit var idsCG : Array<Int> // Arreglo donde se guardan los ids de los chips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sec2)

        //Se guardan los Ids de los radio groups
        idsRG = arrayOf(R.id.rg_q11, R.id.rg_q12,
                        R.id.rg14_1, R.id.rg14_2, R.id.rg14_3, R.id.rg14_4, R.id.rg14_5, R.id.rg14_6, R.id.rg14_7, R.id.rg14_8,
                        R.id.rg_q15_1, R.id.rg15_2, R.id.rg15_3, R.id.rg15_4, R.id.rg15_5, R.id.rg15_6, R.id.rg15_7, R.id.rg15_8,
                        R.id.rg_q17, R.id.rg_q18, R.id.rg_q19)
        idsCG = arrayOf(R.id.cg13, R.id.cg16)

        enviarSec2 = findViewById(R.id.enviarSec2) as Button // Declaracion Button enviarSec1

        enviarSec2.setOnClickListener {
            //val enviado = sharedPref.getBoolean(Constants.KEY_SEND1,false)
            var mensaje = ""
            val admin = AdminSQLiteOpenHelper(this,"Encuesta",null,1)
            val bd = admin.writableDatabase
            val values = ContentValues()
           // values.put("ID",sharedPref.getString(Constants.KEY_NAME, "."))

            Log.d("AYUDA", "aqui llego");
            // Obtengo respuesta de todos los rg
            try {
             //   if (!enviado) {
                    for (i in idsRG) {
                        var respuesta = respuestaRadioButton(i) as Array<CharSequence>
                        values.put("Q"+respuesta.get(0),respuesta.get(1).toString())
                        //mensaje += "\nPregunta: " + respuesta.get(0) + " respuesta seleccionada: " + respuesta.get(
                        //   1
                        //)
                    }
                    //mensaje += "Pregunta: 3 respuesta: " + q3_lugar.selectedItem // El item seleccionado en el spin
                    values.put("Q3",q3_lugar.selectedItem.toString())
                    for (i in idsCG) {
                        var respuesta = respuestaChip(i) as Array<CharSequence>
                        values.put("Q"+respuesta.get(0),respuesta.get(1).toString())
                        //mensaje += "\nPregunta: " + respuesta.get(0) + " respuestas seleccionadas: " + respuesta.get(
                        //       1)
                    }
                    mensaje = "Tus respuestas han sido guardadas."
                    //sharedPref.edit()
                       // .putBoolean(Constants.KEY_SEND1,true)
                        //.apply()

                    //Global.setGlobal(enviado)
                //}
               // else
                  //  mensaje = "¡Ya has enviado tus respuestas! Gracias :)"
            }catch (e: Exception){
                mensaje = "¡No olvides llenar todos los campos!"
            }
            //Imprimo en toast el mensaje

         //   bd.insert("RESPUESTAS",null,values)
           // bd.close()
            Toast.makeText(baseContext, mensaje, Toast.LENGTH_SHORT).show();

        }

    }

    //Funcion que regresa un array CharSequence (¿es String?) En la primera posicion esta el num de pregunta y en la segunda el num de respuesta.
    fun respuestaRadioButton(id: Int): Array<CharSequence> {
        var radioGroup = findViewById(id) as RadioGroup
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
        var radioButton = findViewById(selectedOption) as RadioButton

        var respuesta = arrayOf(radioGroup.contentDescription, radioButton.contentDescription)


        return respuesta
    }

    //Funcion que regresa un array CharSequence (¿es String?) En la primera posicion esta el num de pregunta y en la segunda el num de respuesta.
    fun respuestaChip(id: Int): Array<CharSequence>{
        var ChipGroup = findViewById(id) as ChipGroup
        var lista = ChipGroup.checkedChipIds
        var respuesta = ""
        for (k in lista) {
            var Chip = findViewById(k) as Chip
            respuesta += Chip.contentDescription
            respuesta += ","
        }
        respuesta = respuesta.substring(0, respuesta.length-1)

        var retorno = arrayOf(ChipGroup.contentDescription, respuesta)

        return retorno
    }

}
