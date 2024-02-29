package edu.mirea.onebeattrue.weatherapp

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import edu.mirea.onebeattrue.weatherapp.di.DaggerApplicationComponent

class WeatherApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

val applicationComponent
    @Composable get() = (LocalContext.current.applicationContext as WeatherApp).component