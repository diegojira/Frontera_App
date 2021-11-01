package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.isVisible
import com.cicipn.geointeligencia_anp_app.R
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
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
            R.id.rg_q24_regresa_a,R.id.rg_q25_regresa_a,R.id.rg_q27_regresa_a,R.id.rg_q28_regresa_a,
            R.id.rg_q29_regresa_a,R.id.rg_q30_regresa_a,R.id.rg_q31_regresa_a,R.id.rg_q32_regresa_a,
            R.id.rg_q33_regresa_a,R.id.rg34_1,R.id.rg34_2,R.id.rg34_3,R.id.rg34_4,R.id.rg_q35_regresa_a,
            R.id.rg_q36_regresa_a,R.id.rg_q37_regresa_a,)
        var idCG3 = R.id.cG26
        enviarSec3 = findViewById<Button>(R.id.enviarSec3)
        val q21_extra = findViewById<RadioButton>(R.id.rb21_extra)
        val q23_extra = findViewById<RadioButton>(R.id.rb23_extra)
        val q24_extra = findViewById<RadioButton>(R.id.rb24_b)
        val extra21 = findViewById<EditText>(R.id.extra21)
        val extra23 = findViewById<EditText>(R.id.extra23)
        val extra24 = findViewById<EditText>(R.id.extra24)
        val q29_extra = findViewById<RadioButton>(R.id.rb29_a)
        val q30_extra = findViewById<RadioButton>(R.id.rb30_a)
        val q33_extra = findViewById<RadioButton>(R.id.rb33_a)
        val cG29 = findViewById<ChipGroup>(R.id.cG29)
        val cG30 = findViewById<ChipGroup>(R.id.cG30)
        val cG33 = findViewById<ChipGroup>(R.id.cG33)

        q21_extra.setOnClickListener { extra21.visibility = View.VISIBLE }
        q23_extra.setOnClickListener { extra23.visibility = View.VISIBLE }
        q24_extra.setOnClickListener { extra24.visibility = View.VISIBLE }
        q29_extra.setOnClickListener { cG29.visibility = View.VISIBLE }
        q30_extra.setOnClickListener { cG30.visibility = View.VISIBLE }
        q33_extra.setOnClickListener { cG33.visibility = View.VISIBLE }


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
                        values.put("Q"+respuesta.get(0),respuesta.get(1).toString())
                        //mensaje += "\nPregunta: " + respuesta.get(0) + " respuestas seleccionadas: " + respuesta.get(1)
                    }
                    var respuesta = respuestaChips(idCG3)
                    values.put("Q26",respuesta.get(1).toString())
                    //mensaje += "\nPregunta: "+ respuesta.get(0) + "respuestas seleccionadas: " + respuesta.get(1) + "\n"
                    mensaje += "Tus respuestas han sido guardadas."
                    sharedPref.edit()
                        .putBoolean(KEY_SEND3,true) //Cambiar a true cuando sea version final. False para pruebas
                        .apply()
                }

                else mensaje = "¡Ya has enviado tus respuestas! Gracias :)"

            }
            catch (e: Exception){
                mensaje = "¡No olvides llenar todos los campos!"
            }
            //bd.insert("RESPUESTAS",null,values)
            bd.update("RESPUESTAS",values,"ID = ?", arrayOf(sharedPref.getString(KEY_NAME, ".")))
            bd.delete("RESPUESTAS","Q1 = ?", arrayOf(null)) //Borra la fila con los registros duplicados
            bd.delete("RESPUESTAS","Q1 = ?", arrayOf(null)) //Borra la fila con los registros duplicados
            bd.close()
            Toast.makeText(baseContext, mensaje, Toast.LENGTH_LONG).show()
        }

    }

    fun respuestaRadioButton(id: Int): Array<CharSequence> {
        var radioGroup = findViewById(id) as RadioGroup
        val selectedOption: Int = radioGroup!!.checkedRadioButtonId
        val selectedChip: Int
        var chipA: Chip
        var radioButton = findViewById(selectedOption) as RadioButton
        var respuesta = arrayOf(radioGroup.contentDescription," ")


        when (radioButton.contentDescription){
            "21" -> respuesta[1] = verifyText(extra21)
            "23" -> respuesta[1] = verifyText(extra23)
            "24" -> respuesta[1] = verifyText(extra24)
            "29" -> respuesta[1] = oneChipAnswer(R.id.cG29)
            "30" -> respuesta[1] = oneChipAnswer(R.id.cG30)
            "33" -> respuesta[1] = oneChipAnswer(R.id.cG33)
            else -> respuesta[1] = radioButton.contentDescription

        }
        return respuesta

    }

    fun respuestaChips(id: Int): Array<CharSequence>{
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

    fun oneChipAnswer(id: Int): CharSequence{
        var cG = findViewById<ChipGroup>(id)
        var selectedChip = cG.checkedChipId
        var chip = findViewById<Chip>(selectedChip)
        var respuesta = chip.contentDescription
        return respuesta
    }

    fun verifyText(eT: EditText): CharSequence{
        var respuesta: CharSequence
        if (eT.text.isEmpty()) respuesta = "Sin opinión."
        else respuesta = eT.text
        return respuesta
    }
}