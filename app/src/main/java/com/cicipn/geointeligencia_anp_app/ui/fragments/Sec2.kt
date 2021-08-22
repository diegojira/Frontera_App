package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.cicipn.geointeligencia_anp_app.R

class Sec2 : AppCompatActivity() {

    lateinit var q3_lugar : Spinner //Spinner para los lugares de procedencia
    lateinit var enviarSec2 : Button // Boton que extrae respuestas
    lateinit var idsRG : Array<Int> // Arreglo donde se guardaran los ids e los radiogroups
    lateinit var idsCG : Array<Int> // Arreglo donde se guardan los ids de los chips

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_sec2)
        //Se guardan los Ids de los radio groups
        idsRG = arrayOf(R.id.rg_q11, R.id.rg_q12, R.id.rg14_1, R.id.rg14_2, R.id.rg14_3, R.id.rg14_4, R.id.rg14_5, R.id.rg14_6, R.id.rg14_7,
                        R.id.rg14_8, R.id.rg_q15_1, R.id.rg15_2, R.id.rg15_3, R.id.rg15_4, R.id.rg15_5, R.id.rg15_6, R.id.rg15_7, R.id.rg15_8,
                        R.id.rg_q17, R.id.rg_q18, R.id.rg_q19)
        idsCG = arrayOf(R.id.cg13, R.id.cg16)

        enviarSec2 = findViewById(R.id.enviarSec2) as Button // Declaracion Button enviarSec1

    }
}