package com.example.a66.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a66.presentation.ui.component.LaureatesCard
import com.example.a66.presentation.viewmodel.LaureateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(navHostController: NavHostController, viewModel: LaureateViewModel) {
    val favourites by viewModel.favourites.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Избранное") },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                }
            }
        )

        if (favourites.isEmpty()) {
            Text(
                text = "Список избранного пуст",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 35.dp, bottom = 35.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favourites) { prize ->
                    LaureatesCard(
                        prize = prize,
                        navHostController = navHostController,
                        trailingAction = {
                            IconButton(onClick = { viewModel.delete_favourite(prize) }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Удалить из избранного")
                            }
                        }
                    )
                }
            }
        }
    }
}

