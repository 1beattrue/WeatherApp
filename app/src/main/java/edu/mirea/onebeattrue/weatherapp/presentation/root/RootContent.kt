package edu.mirea.onebeattrue.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import edu.mirea.onebeattrue.weatherapp.presentation.details.DetailsContent
import edu.mirea.onebeattrue.weatherapp.presentation.favourite.FavouriteContent
import edu.mirea.onebeattrue.weatherapp.presentation.search.SearchContent

@Composable
fun RootContent(
    modifier: Modifier = Modifier,
    component: RootComponent
) {
    Children(
        modifier = modifier,
        stack = component.stack,
        animation = stackAnimation(fade())
    ) {
        when (val instance = it.instance) {
            is RootComponent.Child.Details -> {
                DetailsContent(component = instance.component)
            }

            is RootComponent.Child.Favourite -> {
                FavouriteContent(component = instance.component)
            }

            is RootComponent.Child.Search -> {
                SearchContent(component = instance.component)
            }
        }
    }
}