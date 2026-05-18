package com.example.a66.presentation.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a66.navigation.Screen
import com.example.a66.presentation.ui.component.LaureatesCard
import com.example.a66.presentation.viewmodel.UiState
import com.example.a66.presentation.viewmodel.LaureateViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllScreen(navHostController: NavHostController, viewModel: LaureateViewModel) {
    val uiState = viewModel.uiState.collectAsState()
    val yearText = viewModel.yearText.collectAsState()
    val selectedCategory = viewModel.selectedCategory.collectAsState()
    val categories = listOf("", "physics", "chemistry", "literature", "peace", "physiology or medicine")
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "Нобелевские премии")}, actions = {
                IconButton(onClick = {
                    viewModel.loadProfile()
                    navHostController.navigate(Screen.ProfileScreen.route)
                }) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Профиль")
                }
                IconButton(onClick = { navHostController.navigate(Screen.FavouriteScreen.route) }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "Избранное")
                }
                TextButton(onClick = {
                    viewModel.logout()
                    navHostController.navigate(Screen.LoginScreen.route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }) {
                    Text("Выйти")
                }
            },

        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = yearText.value,
                onValueChange = viewModel::onYearTextChanged,
                label = { Text("Год") },
                modifier = Modifier.weight(1f)
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(2f)
            ) {
                OutlinedTextField(
                    value = selectedCategory.value.ifBlank { "Всё" },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Категория") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                ) {
                    categories.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.ifBlank { "Все" }) },
                            onClick = {
                                viewModel.onCategoryChanged(option)
                                expanded = false
                            }
                        )
                    }
                }
            }
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { viewModel.applyFilter() }
            ) {
                Text(text = "Фильтр")
            }
        }
        when (val state = uiState.value) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 35.dp, bottom = 35.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.data) { prize ->
                        LaureatesCard(
                            prize = prize,
                            navHostController = navHostController,
                            trailingAction = {
                                IconButton(onClick = { viewModel.add_favourite(prize) }) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить в избранное")
                                }
                            }
                        )
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

