package com.cicipn.geointeligencia_anp_app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//Base de Datos
class DataDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null,DATABASE_VERSION) {
    private val db: SQLiteDatabase
    private val values: ContentValues
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
}