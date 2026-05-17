package com.example.a66.data.model

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val token: String
)