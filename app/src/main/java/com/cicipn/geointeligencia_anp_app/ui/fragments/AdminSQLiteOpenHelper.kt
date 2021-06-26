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
        db.execSQL("CREATE TABLE "+"RESPUESTAS"+"("+"ID"+
                " TEXT," +
                "Q1"+" TEXT," +
                "Q2"+" TEXT," +
                "Q3"+" TEXT," +
                "Q4"+" TEXT," +
                "Q5"+" TEXT," +
                "Q6"+" TEXT," +
                "Q7"+" TEXT," +
                "Q8"+" TEXT," +
                "Q9"+" TEXT," +
                "Q10"+" TEXT)" )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

}