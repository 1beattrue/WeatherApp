package edu.mirea.onebeattrue.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import edu.mirea.onebeattrue.weatherapp.data.network.api.ApiFactory
import edu.mirea.onebeattrue.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        lifecycleScope.launch {
            val current = apiService.loadCurrent("Moscow")
            val forecast = apiService.loadForecast("Moscow")
            val cities = apiService.searchCity("Moscow")

            Log.d("MainActivity", "$current\n$forecast\n$cities")
        }

        setContent {
            WeatherAppTheme {

            }
        }
    }
}