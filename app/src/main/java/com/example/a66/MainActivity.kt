package com.example.a66

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a66.ui.theme._66Theme
import com.example.a66.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.a66.presentation.viewmodel.LaureateViewModel
import com.example.a66.data.repository.KtorNobelPrizeRepositoryImpl
import com.example.a66.data.repository.RetrofitNobelPrizeRepositoryImpl
import com.example.a66.domain.usecase.FilterNobelPrizeUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val filterUseCase = FilterNobelPrizeUseCase(RetrofitNobelPrizeRepositoryImpl())
        val filterUseCase = FilterNobelPrizeUseCase(KtorNobelPrizeRepositoryImpl())
        val vm = LaureateViewModel(filterUseCase)
        enableEdgeToEdge()
        setContent {
            _66Theme {
                Navigation(navController = rememberNavController(), viewModel = vm)
            }
        }
    }
}

