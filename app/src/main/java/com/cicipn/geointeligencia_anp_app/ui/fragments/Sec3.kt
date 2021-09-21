package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.view.isVisible
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_SEND3
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

        idRG3 = arrayOf(R.id.rg_q20_regresa_a,R.id.rg_q21_regresa_a,R.id.rg_q22_regresa_a)
        enviarSec3 = findViewById<Button>(R.id.enviarSec3)
        val q21_extra = findViewById<RadioButton>(R.id.rb21_extra)
        val extra_ans = findViewById<EditText>(R.id.q21et)




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
                        .putBoolean(KEY_SEND3,true)
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

        var respuesta = arrayOf(radioGroup.contentDescription, radioButton.contentDescription)


        return respuesta
    }
}