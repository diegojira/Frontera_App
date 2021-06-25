package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.SharedPreferences
import java.util.ArrayList

//Aquí se crean las tablas necesarias
class Table {
    abstract class Respuestas_Encuesta{ //Tabla de Respuestas

        //lateinit var sharedPref: SharedPreferences
        companion object{
            val _ID = "id"
            val TABLE_NAME = "RESPUESTAS" //Nombre de la tabla
            val COLUMN_Q1 = "Q1_EDAD"
            val COLUMN_Q2 = "Q2_SEXO"
            val COLUMN_Q3 = "Q3_PROCEDENCIA"
            val COLUMN_Q4 = "Q4"
            val COLUMN_Q5 = "Q5"
            val COLUMN_Q6 = "Q6"
            val COLUMN_Q7 = "Q7"
            val COLUMN_Q8 = "Q8"
            val COLUMN_Q9 = "Q9"
            val COLUMN_Q10 = "Q10"
            /*val COLUMN_Q11 = "Q11"
            val COLUMN_Q12 = "Q12"
            val COLUMN_Q13 = "Q13"
            val COLUMN_Q14A = "Q14_A"
            val COLUMN_Q14B = "Q14_B"
            val COLUMN_Q14C = "Q14_C"
            val COLUMN_Q14D = "Q14_D"
            val COLUMN_Q14E = "Q14_E"
            val COLUMN_Q14F = "Q14_F"
            val COLUMN_Q14G = "Q14_G"
            val COLUMN_Q14H = "Q10_H"
            val COLUMN_Q15A = "Q15_A"
            val COLUMN_Q15B = "Q15_B"
            val COLUMN_Q15C = "Q15_C"
            val COLUMN_Q15D = "Q15_D"
            val COLUMN_Q15E = "Q15_E"
            val COLUMN_Q15F = "Q15_F"
            val COLUMN_Q15G = "Q15_G"
            val COLUMN_Q16 = "Q16"
            val COLUMN_Q17 = "Q17"
            val COLUMN_Q18 = "Q18"
            val COLUMN_Q19 = "Q19"
            val COLUMN_Q20 = "Q20"
            val COLUMN_Q21 = "Q21"
            val COLUMN_Q22 = "Q22"
            val COLUMN_Q23 = "Q23"
            val COLUMN_Q24 = "Q24"
            val COLUMN_Q25 = "Q25"
            val COLUMN_Q26 = "Q26"
            val COLUMN_Q27 = "Q27"
            val COLUMN_Q28 = "Q28"
            val COLUMN_Q29 = "Q29" //Pendiente. Posibles Respuestas Si y No. Si, se activan opciones
            val COLUMN_Q30 = "Q30" //Pendiente. Posibles Respuestas Si y No. Si, se activan opciones
            val COLUMN_Q31 = "Q31"
            val COLUMN_Q32 = "Q32"
            val COLUMN_Q33 = "Q33" //Pendiente. Posibles Respuestas Si y No. Si, se activan opciones
            val COLUMN_Q34A = "Q34_A"
            val COLUMN_Q34B = "Q34_B"
            val COLUMN_Q34C = "Q34_C"
            val COLUMN_Q34D = "Q34_D"
            val COLUMN_Q35 = "Q32"
            val COLUMN_Q36 = "Q32"
            val COLUMN_Q37 = "Q32"*/

        }
    }
}