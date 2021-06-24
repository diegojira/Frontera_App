package com.cicipn.geointeligencia_anp_app.db

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Base de Datos
class DataDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {
    val db: SQLiteDatabase
    //val dbHelper = DataDBHelper(this)
    private val values: ContentValues
    lateinit var sharedPref: SharedPreferences
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Respuestas_Encuestas"
    }

    init { //Inicialización de la base de datos
        db = this.writableDatabase
        values = ContentValues()
    }

    //Método que crea la tabla y sus columnas
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE "+Table.Respuestas_Encuesta.TABLE_NAME+" ("+Table.Respuestas_Encuesta._ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
        Table.Respuestas_Encuesta.COLUMN_Q1+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q2+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q3+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q4+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q5+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q6+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q7+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q8+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q9+" TEXT NOT NULL," +
                Table.Respuestas_Encuesta.COLUMN_Q10+" TEXT NOT NULL," );
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertOnDB(){
        //val db = dbHelper.writableDatabase
        values.apply {
            //put(Table.Respuestas_Encuesta._ID,sharedPref.getString(KEY_))
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
            put(Table.Respuestas_Encuesta.COLUMN_Q10,"Respuesta 10")//Falta agregar el método
        }
        db.insert(Table.Respuestas_Encuesta.TABLE_NAME,null,values)
        db.close()
    }

}