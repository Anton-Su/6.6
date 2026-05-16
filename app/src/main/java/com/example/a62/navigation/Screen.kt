package com.example.a62.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a62.presentation.ui.screen.DetailScreen
import com.example.a62.presentation.viewmodel.LaureateViewModel

sealed class Screen(val route: String) {
    data object PhotoListScreen : Screen("photo_list")
    data object PhotoDetailScreen : Screen("photo_detail/{itemId}") {
        fun createRoute(itemId: Int) = "photo_detail/$itemId"
    }
}

@Composable
fun Navigation(navController: NavHostController = rememberNavController(), viewModel: LaureateViewModel) {
    NavHost(navController, startDestination = Screen.PhotoListScreen.route) {
        composable(Screen.PhotoListScreen.route) {
            com.example.a62.presentation.ui.screen.AllScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            Screen.PhotoDetailScreen.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            val prizes = viewModel.laureates.collectAsState().value
            val item = itemId?.let { id -> prizes.find { it.id.hashCode() == id } }
            if (item != null) {
                DetailScreen(navHostController = navController, viewModel = viewModel, item = item)
            }
        }
    }
}