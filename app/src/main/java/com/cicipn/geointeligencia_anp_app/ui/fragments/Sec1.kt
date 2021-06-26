package com.cicipn.geointeligencia_anp_app.ui.fragments


import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND1
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log


@AndroidEntryPoint
class Sec1 : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPreferences

    lateinit var q3_lugar : Spinner //Spinner para los lugares de procedencia
    lateinit var enviarSec1 : Button // Boton que extrae respuestas
    lateinit var idsRG : Array<Int> // Arreglo donde se guardaran los ids e los radiogroups
    lateinit var idsCG : Array<Int>
    //var enviado = Global.enviado
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sec1)

        //Declaración de Preguntas
        q3_lugar = findViewById(R.id.spinner_3) as Spinner //Declaración Spinner q3

        //Lista de Opciones de Spinners
        val q3_opc = arrayOf("Foreigner","Aguascalientes","Baja California","Baja California Sur","Campeche","Coahuila","CDMX","Colima","Chiapas","Chihuahua",
                "Durango","Estado de México","Guanajuato","Guerrero","Hidalgo","Jalisco","Michoacán","Morelos","Nayarit","Nuevo León","Oaxaca",
                "Puebla","Querétaro","Quintana Roo","San Luís Potosí","Sinaloa","Sonora","Tabasco","Tamaulipas","Tlaxcala","Veracruz","Yucatán",
                "Zacatecas")

        //Pregunta 3
        q3_lugar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,q3_opc)

        //Se guardan los Ids de los radio groups
        idsRG = arrayOf(R.id.rg1, R.id.rg2, R.id.rg5, R.id.rg6, R.id.rg7, R.id.rg8, R.id.rg9, R.id.rg10)
        idsCG = arrayOf(R.id.cg4)
        enviarSec1 = findViewById(R.id.enviarSec1) as Button // Declaracion Button enviarSec1

        enviarSec1.setOnClickListener {
            val enviado = sharedPref.getBoolean(KEY_SEND1,false)
            var mensaje = ""
            val admin = AdminSQLiteOpenHelper(this,"Encuesta",null,1)
            val bd = admin.writableDatabase
            val values = ContentValues()
            values.put("ID",sharedPref.getString(KEY_NAME, "."))
            // Obtengo respuesta de todos los rg
            try {
                if (!enviado) {
                    for (i in idsRG) {
                        var respuesta = respuestaRadioButton(i) as Array<CharSequence>
                        values.put("Q"+respuesta.get(0),respuesta.get(1).toString())
                        mensaje += "\nPregunta: " + respuesta.get(0) + " respuesta seleccionada: " + respuesta.get(
                                1
                        )
                    }
                    mensaje += "Pregunta: 3 respuesta: " + q3_lugar.selectedItem // El item seleccionado en el spin
                    values.put("Q3",q3_lugar.selectedItem.toString())
                    for (i in idsCG) {
                        var respuesta = respuestaChip(i) as Array<CharSequence>
                        values.put("Q"+respuesta.get(0),respuesta.get(1).toString())
                        mensaje += "\nPregunta: " + respuesta.get(0) + " respuestas seleccionadas: " + respuesta.get(
                                1)
                    }
                    //enviado = true
                    sharedPref.edit()
                            .putBoolean(KEY_SEND1,true)
                            .apply()

                    //Global.setGlobal(enviado)
                }
                else
                    mensaje = "¡Ya has enviado tus respuestas! Gracias :)"
            }catch (e: Exception){
                mensaje = "¡No olvides llenar todos los campos!"
            }
            //Imprimo en toast el mensaje

            /*values.apply {
                put(Table.Respuestas_Encuesta._ID,sharedPref.getString(Constants.KEY_NAME, "."))
                //Falta sustituir las strings por las respuestas
                put(Table.Respuestas_Encuesta.COLUMN_Q1,"1")
                put(Table.Respuestas_Encuesta.COLUMN_Q2,"Respuesta 20")
                put(Table.Respuestas_Encuesta.COLUMN_Q3,"Respuesta 30")
                put(Table.Respuestas_Encuesta.COLUMN_Q4,"Respuesta 40")
                put(Table.Respuestas_Encuesta.COLUMN_Q5,"Respuesta 50")
                put(Table.Respuestas_Encuesta.COLUMN_Q6,"Respuesta 60")
                put(Table.Respuestas_Encuesta.COLUMN_Q7,"Respuesta 70")
                put(Table.Respuestas_Encuesta.COLUMN_Q8,"Respuesta 8")
                put(Table.Respuestas_Encuesta.COLUMN_Q9,"Respuesta 9")
                put(Table.Respuestas_Encuesta.COLUMN_Q10,"Respuesta 10")

            }*/
            bd.insert("RESPUESTAS",null,values)
            bd.close()
            Toast.makeText(baseContext, mensaje, Toast.LENGTH_SHORT).show();
        }




        //q3_lugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        //}
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

