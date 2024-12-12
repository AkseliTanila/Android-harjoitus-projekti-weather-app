package com.example.finalprojectweatherapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlin.math.roundToInt

@Composable
fun HomeView(navController: NavHostController, forecast: WeatherResponse?, isLoading: Boolean){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.loading_weather_data), color = MaterialTheme.colorScheme.onPrimaryContainer)
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            }
        } else if (forecast?.list != null && forecast.list.isNotEmpty()) {
            // Title container
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = forecast.city.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 30.sp
                )
            }

            // Current weather container
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .shadow(8.dp, RoundedCornerShape(30.dp))
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(30.dp)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(30.dp)
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        // Main weather params
                        Row(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "${forecast.list[0].main.temp.roundToInt()}°",
                                fontSize = 80.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(70.dp))
                            val iconUrl = "https://openweathermap.org/img/wn/${forecast.list[0].weather[0].icon}@2x.png"
                            AsyncImage(
                                model = iconUrl,
                                contentDescription = "Weather Icon",
                                modifier = Modifier.size(120.dp)
                            )
                        }

                        // Other weather params
                        Column(modifier = Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "${forecast.list[0].main.temp_max.roundToInt()}° / ${forecast.list[0].main.temp_min.roundToInt()}°   " + stringResource(R.string.feels_like) + " ${forecast.list[0].main.feels_like.roundToInt()}°",
                                fontSize = 22.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val roundedWindSpeed = String.format("%.1f", forecast.list[0].wind.speed)
                            Text(
                                text = stringResource(R.string.wind_speed) + " ${roundedWindSpeed}m/s",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.humidity) + " ${forecast.list[0].main.humidity}%",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                    }
                }
            }

            // Bottom LazyColumn container
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f)
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .shadow(8.dp, RoundedCornerShape(30.dp))
                        .background(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            RoundedCornerShape(30.dp)
                        )
                        .border(
                            width = 0.5.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(30.dp)
                        )
                        .weight(4f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        LazyRow{
                            items(forecast.list) { weather ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .width(50.dp),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(0.dp),
                                        verticalArrangement = Arrangement.spacedBy(1.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        val dateOfWeather = weather.dt_txt.split(" ")[0].substring(8, 10) + "." + weather.dt_txt.split(" ")[0].substring(5, 7)
                                        Text(
                                            text = dateOfWeather,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 10.sp)
                                        )

                                        val timeOfWeather = weather.dt_txt.split(" ")[1].substring(0, 5)
                                        Text(
                                            text = timeOfWeather,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            style = MaterialTheme.typography.bodySmall.copy(lineHeight = 10.sp)
                                        )
                                    }

                                    val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
                                    AsyncImage(
                                        model = iconUrl,
                                        contentDescription = "Weather Icon",
                                        modifier = Modifier.size(50.dp)
                                    )

                                    Text(
                                        text = "${weather.main.temp.roundToInt()}°",
                                        fontSize = 20.sp,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                ) {
                    val context = LocalContext.current
                    val url = "https://openweathermap.org/weathermap?basemap=map&cities=false&layer=radar&lat=${forecast.city.coord.lat}&lon=${forecast.city.coord.lon}&zoom=10"
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.height(80.dp).padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.open_precipitation_map_title),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.unable_to_load_data), color = MaterialTheme.colorScheme.onPrimaryContainer)
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
