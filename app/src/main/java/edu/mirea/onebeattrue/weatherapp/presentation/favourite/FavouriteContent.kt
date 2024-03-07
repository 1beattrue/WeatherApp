package edu.mirea.onebeattrue.weatherapp.presentation.favourite

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import edu.mirea.onebeattrue.weatherapp.R
import edu.mirea.onebeattrue.weatherapp.presentation.extensions.tempToFormattedString
import edu.mirea.onebeattrue.weatherapp.presentation.ui.theme.CardGradients
import edu.mirea.onebeattrue.weatherapp.presentation.ui.theme.Gradient

@Composable
fun FavouriteContent(
    modifier: Modifier = Modifier,
    component: FavouriteComponent
) {
    val state by component.model.collectAsState()

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(2) }) { SearchCard(onClick = { component.onClickSearch() }) }
        itemsIndexed(
            items = state.cityItems,
            key = { _, item -> item.city.id }
        ) { index, item ->
            CityCard(
                cityItem = item,
                index = index,
                onClick = { component.onCityItemClick(item.city) }
            )
        }
        item {
            AddFavouriteCityCard(
                onClick = { component.onClickAddFavourite() }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CityCard(
    modifier: Modifier = Modifier,
    cityItem: FavouriteStore.State.CityItem,
    index: Int,
    onClick: () -> Unit
) {
    val gradient = getGradientByIndex(index)
    Card(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = 16.dp,
                spotColor = gradient.shadowColor,
                shape = MaterialTheme.shapes.extraLarge
            ),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(
            modifier = Modifier
                .background(gradient.primaryGradient)
                .fillMaxSize()
                .sizeIn(minHeight = 196.dp)
                .drawBehind {
                    drawCircle(
                        brush = gradient.secondaryGradient,
                        center = Offset(
                            x = center.x - size.width / 10,
                            y = center.y + size.height / 2
                        ),
                        radius = size.maxDimension / 2
                    )
                }
                .clickable { onClick() }
                .padding(24.dp)
        ) {
            when (val weatherState = cityItem.weatherState) {
                FavouriteStore.State.WeatherState.Initial -> {}

                FavouriteStore.State.WeatherState.Error -> {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        imageVector = Icons.Rounded.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background
                    )
                }

                is FavouriteStore.State.WeatherState.Loaded -> {
                    GlideImage(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        model = weatherState.iconUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(bottom = 24.dp),
                        text = weatherState.tempC.tempToFormattedString(),
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 36.sp)
                    )
                }

                FavouriteStore.State.WeatherState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(56.dp),
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
            Text(
                modifier = Modifier.align(Alignment.BottomStart),
                text = cityItem.city.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.background
            )
        }
    }
}

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val gradient = CardGradients.gradients[3]
    Card(
        modifier = modifier,
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .background(gradient.primaryGradient)
        ) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
            )
            Text(
                text = stringResource(R.string.search),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun AddFavouriteCityCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = MaterialTheme.shapes.extraLarge,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
    ) {
        Column(
            modifier = Modifier
                .sizeIn(minHeight = 196.dp)
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(24.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .size(56.dp),
                imageVector = Icons.Rounded.Edit,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(R.string.button_add_favourite),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

private fun getGradientByIndex(index: Int): Gradient {
    val gradients = CardGradients.gradients
    return gradients[index % gradients.size]
}