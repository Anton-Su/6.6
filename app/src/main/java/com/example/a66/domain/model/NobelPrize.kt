package com.example.a66.domain.model


data class NobelPrize(
    val id: Int,
    val year: Int,
    val category: String,
    val laureates: List<Laureate>
)



