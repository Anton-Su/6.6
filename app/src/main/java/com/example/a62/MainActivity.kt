package com.example.a62

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a62.ui.theme._62Theme
import com.example.a62.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.a62.presentation.viewmodel.LaureateViewModel
import com.example.a62.data.repository.KtorLaureateRepositoryImpl
import com.example.a62.data.repository.RetrofitLaureateRepositoryImpl
import com.example.a62.domain.usecase.FilterLaureatesUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filterUseCase = FilterLaureatesUseCase(RetrofitLaureateRepositoryImpl())
        // val filterUseCase = FilterLaureatesUseCase(KtorLaureateRepositoryImpl())
        val vm = LaureateViewModel(filterUseCase)
        enableEdgeToEdge()
        setContent {
            _62Theme {
                Navigation(navController = rememberNavController(), viewModel = vm)
            }
        }
    }
}
