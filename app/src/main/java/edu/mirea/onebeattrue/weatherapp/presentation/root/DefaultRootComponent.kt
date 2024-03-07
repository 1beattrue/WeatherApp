package edu.mirea.onebeattrue.weatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import edu.mirea.onebeattrue.weatherapp.domain.entity.City
import edu.mirea.onebeattrue.weatherapp.presentation.details.DefaultDetailsComponent
import edu.mirea.onebeattrue.weatherapp.presentation.favourite.DefaultFavouriteComponent
import edu.mirea.onebeattrue.weatherapp.presentation.search.DefaultSearchComponent
import edu.mirea.onebeattrue.weatherapp.presentation.search.OpenReason
import kotlinx.parcelize.Parcelize

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    override val stack: Value<ChildStack<*, RootComponent.Child>>
        get() = TODO("Not yet implemented")

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is Config.Details -> {
            val component = detailsComponentFactory.create(
                city = config.city,
                onBackClicked = {},
                componentContext = componentContext
            )
            RootComponent.Child.Details(component)
        }

        Config.Favourite -> {
            val component = favouriteComponentFactory.create(
                onCityItemClicked = {},
                onAddToFavouriteClicked = {},
                onSearchClicked = {},
                componentContext = componentContext
            )
            RootComponent.Child.Favourite(component)
        }

        is Config.Search -> {
            val component = searchComponentFactory.create(
                openReason = config.openReason,
                onOpenForecastClicked = {},
                onSaveToFavouriteClicked = {},
                onBackClicked = {},
                componentContext = componentContext
            )
            RootComponent.Child.Search(component)
        }
    }


    sealed interface Config : Parcelable {
        @Parcelize
        data object Favourite : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config

        @Parcelize
        data class Details(val city: City) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): RootComponent
    }
}