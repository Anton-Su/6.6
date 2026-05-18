package com.example.a66.domain.usecase

import com.example.a66.domain.model.NobelPrize
import com.example.a66.domain.model.User
import com.example.a66.domain.repository.UserRepository

class AddFavouriteUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(prize: NobelPrize): User {
        return repository.addFavourite(prize)
    }
}

