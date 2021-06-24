package com.cicipn.geointeligencia_anp_app.db

import android.content.SharedPreferences
import java.util.ArrayList

//Aquí se crean las tablas necesarias
class Table {
    abstract class Respuestas_Encuesta{ //Tabla de Respuestas

        //lateinit var sharedPref: SharedPreferences
        companion object{
            val _ID = "id"
            //val USER = shar
            val TABLE_NAME = "Respuestas" //Nombre de la tabla
            val COLUMN_Q1 = "q1_edad"
            val COLUMN_Q2 = "q2_sexo"
            val COLUMN_Q3 = "q2"
            val COLUMN_Q4 = "q3"
            val COLUMN_Q5 = "q4"
            val COLUMN_Q6 = "q5"
            val COLUMN_Q7 = "q6"
            val COLUMN_Q8 = "q7"
            val COLUMN_Q9 = "q8"
            val COLUMN_Q10 = "q8"
        }
    }
}