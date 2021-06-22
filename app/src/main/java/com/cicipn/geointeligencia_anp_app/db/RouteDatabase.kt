package com.cicipn.geointeligencia_anp_app.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Route::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RouteDatabase : RoomDatabase() {

    abstract fun getRouteDao(): RouteDAO
}