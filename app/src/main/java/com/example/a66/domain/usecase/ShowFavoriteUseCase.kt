package com.example.a66.domain.usecase

import com.example.a66.domain.model.NobelPrize
import com.example.a66.domain.repository.UserRepository

class ShowFavoriteUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): List<NobelPrize> {
        return repository.showFavorite()
    }
}

