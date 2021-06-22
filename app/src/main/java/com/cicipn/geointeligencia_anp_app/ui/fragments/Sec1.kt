package com.cicipn.geointeligencia_anp_app.ui.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.cicipn.geointeligencia_anp_app.R

class Sec1 : AppCompatActivity() {

    lateinit var q3_lugar : Spinner //Spinner para los lugares de procedencia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sec1)

        //Declaración de Preguntas
        q3_lugar = findViewById(R.id.q3_spinner) as Spinner //Declaración Spinner q3

        //Lista de Opciones de Spinners
        val q3_opc = arrayOf("Foreigner","Aguascalientes","Baja California","Baja California Sur","Campeche","Coahuila","CDMX","Colima","Chiapas","Chihuahua",
                "Durango","Estado de México","Guanajuato","Guerrero","Hidalgo","Jalisco","Michoacán","Morelos","Nayarit","Nuevo León","Oaxaca",
                "Puebla","Querétaro","Quintana Roo","San Luís Potosí","Sinaloa","Sonora","Tabasco","Tamaulipas","Tlaxcala","Veracruz","Yucatán",
                "Zacatecas")

        //Pregunta 3
        q3_lugar.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,q3_opc)

        //q3_lugar.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        //}
    }
}