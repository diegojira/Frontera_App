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
                //"Q10"+" TEXT);")
                "Q10"+" TEXT,"+
                "Q11"+" TEXT,"+
                "Q12"+" TEXT,"+
                "Q13"+" TEXT,"+
                "Q14a"+" TEXT,"+
                "Q14b"+" TEXT,"+
                "Q14c"+" TEXT,"+
                "Q14d"+" TEXT,"+
                "Q14e"+" TEXT,"+
                "Q14f"+" TEXT,"+
                "Q14g"+" TEXT,"+
                "Q14h"+" TEXT,"+
                "Q15a"+" TEXT,"+
                "Q15b"+" TEXT,"+
                "Q15c"+" TEXT,"+
                "Q15d"+" TEXT,"+
                "Q15e"+" TEXT,"+
                "Q15f"+" TEXT,"+
                "Q15g"+" TEXT,"+
                "Q15h"+" TEXT,"+
                "Q16"+" TEXT," +
                "Q17"+" TEXT," +
                "Q18"+" TEXT," +
                "Q19"+" TEXT," +
                "Q20"+" TEXT," +
                "Q21"+" TEXT," +
                "Q22"+" TEXT," +
                "Q23"+" TEXT," +
                "Q24"+" TEXT," +
                "Q25"+" TEXT," +
                "Q26"+" TEXT," +
                "Q27"+" TEXT," +
                "Q28"+" TEXT," +
                "Q29"+" TEXT," +
                "Q30"+" TEXT," +
                "Q31"+" TEXT," +
                "Q32"+" TEXT," +
                "Q33"+" TEXT," +
                "Q34a"+" TEXT," +
                "Q34b"+" TEXT," +
                "Q34c"+" TEXT," +
                "Q34d"+" TEXT," +
                "Q35"+" TEXT," +
                "Q36"+" TEXT," +
                "Q37"+" TEXT);")
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

}