package edu.mirea.onebeattrue.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import edu.mirea.onebeattrue.weatherapp.WeatherApp
import edu.mirea.onebeattrue.weatherapp.presentation.root.DefaultRootComponent
import edu.mirea.onebeattrue.weatherapp.presentation.root.RootContent
import edu.mirea.onebeattrue.weatherapp.presentation.ui.theme.WeatherAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApp).component.inject(this)
        super.onCreate(savedInstanceState)

        val component = rootComponentFactory.create(defaultComponentContext())

        setContent {
            WeatherAppTheme {
                RootContent(component = component)
            }
        }
    }
}