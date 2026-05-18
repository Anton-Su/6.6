package com.example.a66.domain.model


data class NobelPrize(
    val id: String,
    val year: String,
    val category: String,
    val laureates: List<Laureate>
)



