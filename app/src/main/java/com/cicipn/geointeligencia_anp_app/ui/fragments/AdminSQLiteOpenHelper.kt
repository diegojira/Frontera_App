package com.cicipn.geointeligencia_anp_app.ui.fragments

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase.CursorFactory

//Base de Datos
class AdminSQLiteOpenHelper(context: Context, name: String, factory: CursorFactory?, version: Int):
        SQLiteOpenHelper(context, name, factory,version) {

    //Método que crea la tabla y sus columnas
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE "+Table.Respuestas_Encuesta.TABLE_NAME+" ("+Table.Respuestas_Encuesta._ID+
                " TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q1+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q2+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q3+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q4+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q5+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q6+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q7+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q8+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q9+" TEXT," +
                Table.Respuestas_Encuesta.COLUMN_Q10+" TEXT)" )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}