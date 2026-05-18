package com.example.a66

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.a66.data.remote.TokenManager
import com.example.a66.data.repository.AuthRepositoryImpl
import com.example.a66.data.repository.UserRepositoryImpl
import com.example.a66.ui.theme._66Theme
import com.example.a66.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.a66.presentation.viewmodel.LaureateViewModel
import com.example.a66.data.repository.KtorNobelPrizeRepositoryImpl
import com.example.a66.domain.usecase.FilterNobelPrizeUseCase
import com.example.a66.domain.usecase.AddFavouriteUseCase
import com.example.a66.domain.usecase.RemoveFavouriteUseCase
import com.example.a66.domain.usecase.GetProfileUseCase
import com.example.a66.domain.usecase.ShowFavoriteUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filterUseCase = FilterNobelPrizeUseCase(KtorNobelPrizeRepositoryImpl())
        val authRepository = AuthRepositoryImpl(TokenManager(applicationContext))
        val userRepository = UserRepositoryImpl()
        val addFavouriteUseCase = AddFavouriteUseCase(userRepository)
        val removeFavouriteUseCase = RemoveFavouriteUseCase(userRepository)
        val getProfileUseCase = GetProfileUseCase(userRepository)
        val showFavoriteUseCase = ShowFavoriteUseCase(userRepository)
        val vm = LaureateViewModel(
            filterUseCase,
            authRepository,
            addFavouriteUseCase,
            removeFavouriteUseCase,
            getProfileUseCase,
            showFavoriteUseCase
        )
        enableEdgeToEdge()
        setContent {
            _66Theme {
                Navigation(navController = rememberNavController(), viewModel = vm)
            }
        }
    }
}

