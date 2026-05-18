package com.example.a66.domain.usecase

import com.example.a66.domain.model.User
import com.example.a66.domain.repository.UserRepository

class GetProfileUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): User {
        return repository.getProfile()
    }
}

