package com.example.a66.data.repository

import com.example.a66.data.model.LoginResponse
import com.example.a66.data.remote.KtorClient
import com.example.a66.data.remote.TokenManager
import com.example.a66.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<String> = runCatching {
        val response: LoginResponse = KtorClient.login(username, password)

        tokenManager.saveAccessToken(response.token)
        // tokenManager.saveRefreshToken(response.refreshToken)

        KtorClient.updateAccessToken(response.token)

        response.token
    }

    override suspend fun logout() {
        tokenManager.clearTokens()
        KtorClient.clearTokens()
    }
}

