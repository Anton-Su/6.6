package com.example.a62.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import coil.compose.AsyncImage
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a62.domain.model.NobelPrize
import com.example.a62.presentation.viewmodel.LaureateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navHostController: NavHostController, viewModel: LaureateViewModel, item: NobelPrize) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(text = "Детали премии") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)) {
                Text(text = "${item.year} • ${item.category}", style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(12.dp))
                Text(text = "Лауреаты:")
                Spacer(Modifier.height(12.dp))
                item.laureates.forEachIndexed { index, laureate ->
                    Text(text = "Лауреат ${index + 1}): ${laureate.fullName}", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Вклад: ${laureate.portion}", style = MaterialTheme.typography.bodyLarge)
                    Log.e("DetailScreen", "Laureate ${laureate.fullName} has portrait URL: ${laureate.portraitUrl}")
                    AsyncImage(
                        model = laureate.portraitUrl ?: "https://placehold.co/",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = Crop
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(text = "Мотивация: " + (laureate.motivation.ifEmpty { "Не указана" }), style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                    val country = laureate.birthCountry
                    val place = laureate.birthPlace
                    Text(text = "Страна: ${country ?: "Неизвестно"} ${place?.let { ", $it" } ?: ""}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
