package com.example.a66.data.remote


import com.example.a66.data.model.LoginResponse
import com.example.a66.data.model.NobelPrizeDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


object KtorClient {
    private var currentAccessToken: String? = null

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }
        install(Auth) {
            bearer {
                loadTokens {
                    currentAccessToken?.let {
                        BearerTokens(it, "")
                    }
                }
                sendWithoutRequest { request ->
                    request.url.build().encodedPath.startsWith("/users")
                }
            }
        }



    }
    suspend fun fetchLaureates(
        limit: Int = 50,
        offset: Int = 0
    ): List<NobelPrizeDto> {
        return client.get("http://10.0.2.2:8080/prizes") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<List<NobelPrizeDto>>()
    }

    suspend fun login(username: String, password: String): LoginResponse {
        return client.post("http://10.0.2.2:8080/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                mapOf(
                    "username" to username,
                    "password" to password
                )
            )
        }.body()
    }

    fun updateAccessToken(token: String?) {
        currentAccessToken = token
    }

    fun clearTokens() {
        currentAccessToken = null
    }
}

