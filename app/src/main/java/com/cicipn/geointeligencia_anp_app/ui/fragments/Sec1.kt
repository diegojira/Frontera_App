package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Sec1 : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sec1)

        //Declaración de elementos
        lateinit var q3_lugar : Spinner //Spinner para los lugares de procedencia
        val btn_fin1 = findViewById<Button>(R.id.btn_finSec1)
        //val et_prueba =findViewById<EditText>(R.id.et_prueba)

        //Declaración de Preguntas
        q3_lugar = findViewById(R.id.q3_spinner) as Spinner //Declaración Spinner q3

        //Lista de Opciones de Spinners
        val q3_opc = arrayOf(" ","Foreigner","Aguascalientes","Baja California","Baja California Sur","Campeche","Coahuila","CDMX","Colima","Chiapas","Chihuahua",
                "Durango","Estado de México","Guanajuato","Guerrero","Hidalgo","Jalisco","Michoacán","Morelos","Nayarit","Nuevo León","Oaxaca",
                "Puebla","Querétaro","Quintana Roo","San Luís Potosí","Sinaloa","Sonora","Tabasco","Tamaulipas","Tlaxcala","Veracruz","Yucatán",
                "Zacatecas")

        //Pregunta 3
        q3_lugar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,q3_opc)

        //Botón para Finalizar Encuesta Sección 1
        btn_fin1.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"Encuesta",null,1)
            val bd = admin.writableDatabase
            val values = ContentValues()
            values.apply {
                put(Table.Respuestas_Encuesta._ID,sharedPref.getString(KEY_NAME, "."))
                //Falta sustituir las strings por las respuestas
                put(Table.Respuestas_Encuesta.COLUMN_Q1,"Respuesta 1")
                put(Table.Respuestas_Encuesta.COLUMN_Q2,"Respuesta 2")
                put(Table.Respuestas_Encuesta.COLUMN_Q3,"Respuesta 3")
                put(Table.Respuestas_Encuesta.COLUMN_Q4,"Respuesta 4")
                put(Table.Respuestas_Encuesta.COLUMN_Q5,"Respuesta 5")
                put(Table.Respuestas_Encuesta.COLUMN_Q6,"Respuesta 6")
                put(Table.Respuestas_Encuesta.COLUMN_Q7,"Respuesta 7")
                put(Table.Respuestas_Encuesta.COLUMN_Q8,"Respuesta 8")
                put(Table.Respuestas_Encuesta.COLUMN_Q9,"Respuesta 9")
                put(Table.Respuestas_Encuesta.COLUMN_Q10,"Respuesta 10")

            }
            bd.insert(Table.Respuestas_Encuesta.TABLE_NAME,null,values)
            bd.close()
            Toast.makeText(this,"Registro Exitoso de Sección 1",Toast.LENGTH_SHORT).show()
        }


    }

}