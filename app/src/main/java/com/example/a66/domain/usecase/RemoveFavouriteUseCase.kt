package com.example.a66.domain.usecase

import com.example.a66.domain.model.User
import com.example.a66.domain.repository.UserRepository

class RemoveFavouriteUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(prizeId: String): User {
        return repository.removeFavourite(prizeId)
    }
}

