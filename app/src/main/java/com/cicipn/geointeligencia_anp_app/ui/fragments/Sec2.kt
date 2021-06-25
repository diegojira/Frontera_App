package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.cicipn.geointeligencia_anp_app.R

class Sec2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec2)
        val btn2 = findViewById<Button>(R.id.button2)

        /*btn2.setOnClickListener{
            val admin = AdminSQLiteOpenHelper(this,"administracion", null, 1)
            val bd = admin.writableDatabase
            val registro = ContentValues()
            registro.put("respuesta", "Resultado 1")
            bd.insert("articulos", null, registro)
            bd.close()
            Toast.makeText(this, "Se cargó respuesta", Toast.LENGTH_SHORT).show()
        }*/
    }
}