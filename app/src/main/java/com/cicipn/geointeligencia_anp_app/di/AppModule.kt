package com.cicipn.geointeligencia_anp_app.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.cicipn.geointeligencia_anp_app.db.RouteDatabase
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.cicipn.geointeligencia_anp_app.other.Constants.KEY_NAME
import com.cicipn.geointeligencia_anp_app.other.Constants.ROUTE_DATABASE_NAME
import com.cicipn.geointeligencia_anp_app.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRouteDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RouteDatabase::class.java,
            ROUTE_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRouteDao(db: RouteDatabase) = db.getRouteDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app:Context) =
            app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideNickname(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) = sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)


}