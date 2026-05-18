package com.example.a66.domain.model


data class User(
    val id: Int,
    val username: String,
    val gender: String,
    val age: Int,
    val favoritePrizes: List<NobelPrize>
)