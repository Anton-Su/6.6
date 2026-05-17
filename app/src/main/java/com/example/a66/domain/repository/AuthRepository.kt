package com.example.a66.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<String>
    suspend fun logout()
}