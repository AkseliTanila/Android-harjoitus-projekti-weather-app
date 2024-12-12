package com.example.finalprojectweatherapp

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(navController: NavHostController, themeViewModel: ThemeViewModel, itemClass: StoredItemsClass, items: Pair<String, Boolean>){
    val isDarkTheme by themeViewModel.isDarkTheme
    var cityName by remember { mutableStateOf(items.first) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = stringResource(R.string.settings_page_title),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(60.dp))

        // Change the fetched weather data location (city)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text(text = stringResource(R.string.target_city_textbox_title), color = MaterialTheme.colorScheme.onSecondaryContainer) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        itemClass.saveValues(cityName, isDarkTheme)
                        } },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .height(80.dp)
                        .padding(16.dp)
                    ) {
                    Text(
                        text = stringResource(R.string.save_city_button_text),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Toggle switch for light and dark theme
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
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = if (themeViewModel.isDarkTheme.value) stringResource(R.string.dark_theme_indicator) else stringResource(
                        R.string.light_theme_indicator
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 18.sp
                )
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = {
                        themeViewModel.toggleTheme()
                        CoroutineScope(Dispatchers.IO).launch {
                            itemClass.saveValues(items.first, isDarkTheme)
                        }
                    }
                )
            }

        }
    }
}