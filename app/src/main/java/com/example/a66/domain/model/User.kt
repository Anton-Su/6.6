package com.example.a66.domain.model


data class User(
    val id: String,
    val username: String,
    val gender: String,
    val age: String,
    val favoritePrizes: List<NobelPrize>
)