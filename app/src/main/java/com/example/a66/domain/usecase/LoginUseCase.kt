package com.example.a66.domain.usecase

import com.example.a66.domain.repository.AuthRepository


class LoginUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<String> {
        return repository.login(username, password)
    }
}