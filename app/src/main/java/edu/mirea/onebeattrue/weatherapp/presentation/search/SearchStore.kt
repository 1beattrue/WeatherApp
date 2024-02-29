package edu.mirea.onebeattrue.weatherapp.presentation.search

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import edu.mirea.onebeattrue.weatherapp.domain.entity.City
import edu.mirea.onebeattrue.weatherapp.domain.usecase.AddCityToFavouriteUseCase
import edu.mirea.onebeattrue.weatherapp.domain.usecase.SearchCityUseCase
import edu.mirea.onebeattrue.weatherapp.presentation.search.SearchStore.Intent
import edu.mirea.onebeattrue.weatherapp.presentation.search.SearchStore.Label
import edu.mirea.onebeattrue.weatherapp.presentation.search.SearchStore.State
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

interface SearchStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeSearchQuery(val query: String) : Intent
        data object ClickSearch : Intent
        data object ClickBack : Intent
        data class ClickCity(val city: City) : Intent
    }

    data class State(
        val searchQuery: String,
        val searchState: SearchState
    ) {
        sealed interface SearchState {
            data object Initial : SearchState
            data object Loading : SearchState
            data object Error : SearchState
            data object EmptyResult : SearchState
            data class SuccessLoaded(val cities: List<City>) : SearchState
        }
    }

    sealed interface Label {
        data object ClickBack : Label
        data object SaveToFavourite : Label
        data class OpenForecast(val city: City) : Label
    }
}

class SearchStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val searchCityUseCase: SearchCityUseCase,
    private val addCityToFavouriteUseCase: AddCityToFavouriteUseCase
) {

    fun create(openReason: OpenReason): SearchStore =
        object : SearchStore, Store<Intent, State, Label> by storeFactory.create(
            name = STORE_NAME,
            initialState = State(
                searchQuery = "",
                searchState = State.SearchState.Initial
            ),
            bootstrapper = BootstrapperImpl(),
            executorFactory = { ExecutorImpl(openReason) },
            reducer = ReducerImpl
        ) {}

    private sealed interface Action

    private sealed interface Msg {
        data class ChangeSearchQuery(val query: String) : Msg
        data object Loading : Msg
        data object Error : Msg
        data class Result(
            val cities: List<City>
        ) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
        }
    }

    private inner class ExecutorImpl(
        private val openReason: OpenReason
    ) : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        private var searchJob: Job? = null

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ChangeSearchQuery -> {
                    dispatch(Msg.ChangeSearchQuery(intent.query))
                }

                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }

                is Intent.ClickCity -> {
                    when (openReason) {
                        OpenReason.AddToFavourite -> {
                            scope.launch {
                                addCityToFavouriteUseCase(intent.city)
                                publish(Label.SaveToFavourite)
                            }
                        }

                        OpenReason.RegularSearch -> {
                            publish(Label.OpenForecast(intent.city))
                        }
                    }
                }

                Intent.ClickSearch -> {
                    searchJob?.cancel()
                    searchJob = scope.launch {
                        dispatch(Msg.Loading)
                        try {
                            val cities = searchCityUseCase(getState().searchQuery)
                            dispatch(Msg.Result(cities))
                        } catch (_: Exception) {
                            dispatch(Msg.Error)
                        }
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when (msg) {
            is Msg.ChangeSearchQuery -> {
                copy(searchQuery = msg.query)
            }
            Msg.Error -> {
                copy(searchState = State.SearchState.Error)
            }
            Msg.Loading -> {
                copy(searchState = State.SearchState.Loading)
            }
            is Msg.Result -> {
                val searchState = if (msg.cities.isNotEmpty()) {
                    State.SearchState.SuccessLoaded(msg.cities)
                } else {
                    State.SearchState.EmptyResult
                }
                copy(searchState = searchState)
            }
        }
    }

    companion object {
        const val STORE_NAME = "SearchStore"
    }
}
