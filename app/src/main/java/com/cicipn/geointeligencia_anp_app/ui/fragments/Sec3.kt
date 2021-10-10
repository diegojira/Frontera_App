package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND3
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sec3.*
import javax.inject.Inject

@AndroidEntryPoint
class Sec3 : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPreferences
    lateinit var enviarSec3: Button
    lateinit var idRG3: Array<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec3)

        idRG3 = arrayOf(R.id.rg_q20_regresa_a,R.id.rg_q21_regresa_a,R.id.rg_q22_regresa_a,R.id.rg_q23_regresa_a,
            R.id.rg_q24_regresa_a)
        //idCB3 = arrayOf()
        enviarSec3 = findViewById<Button>(R.id.enviarSec3)
        val q21_extra = findViewById<RadioButton>(R.id.rb21_extra)
        val q23_extra = findViewById<RadioButton>(R.id.rb23_extra)
        val q24_extra = findViewById<RadioButton>(R.id.rb24_b)
        val extra21 = findViewById<EditText>(R.id.extra21)
        val extra23 = findViewById<EditText>(R.id.extra23)
        val extra24 = findViewById<EditText>(R.id.extra24)

        q21_extra.setOnClickListener { extra21.visibility = View.VISIBLE }
        q23_extra.setOnClickListener { extra23.visibility = View.VISIBLE }
        q24_extra.setOnClickListener { extra24.visibility = View.VISIBLE }

        enviarSec3.setOnClickListener {
            var enviado = sharedPref.getBoolean(KEY_SEND3,false)
            var mensaje = ""
            val admin = AdminSQLiteOpenHelper(this,"Encuesta",null,1)
            val bd = admin.writableDatabase
            val values = ContentValues()
            try {
                if(!enviado){
                    for (i in idRG3){
                        var respuesta = respuestaRadioButton(i)
                        mensaje += "\nPregunta: " + respuesta.get(0) + " respuestas seleccionadas: " + respuesta.get(1)
                    }
                    mensaje += "Tus respuestas han sido guardadas."
                    sharedPref.edit()
                        .putBoolean(KEY_SEND3,false) //Cambiar a true cuando sea version final. False para pruebas
                        .apply()
                }

                else mensaje = "¡Ya has enviado tus respuestas! Gracias :)"

            }
            catch (e: Exception){
                mensaje = "¡No olvides llenar todos los campos!"
            }
            Toast.makeText(baseContext, mensaje, Toast.LENGTH_SHORT).show()
        }

    }

    fun respuestaRadioButton(id: Int): Array<CharSequence> {
        var radioGroup = findViewById(id) as RadioGroup
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
        var radioButton = findViewById(selectedOption) as RadioButton
        var respuesta = arrayOf(radioGroup.contentDescription," ")


        when (radioButton.contentDescription){
            "21" -> respuesta[1] = extra21.text
            "23" -> respuesta[1] = extra23.text
            "24" -> respuesta[1] = extra24.text
            else -> respuesta[1] = radioButton.contentDescription

        }
        return respuesta
    }

    fun respuestaCheck(id: Int): Array<CharSequence>{
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