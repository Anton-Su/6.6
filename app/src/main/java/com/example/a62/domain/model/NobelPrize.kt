package com.example.a62.domain.model


data class NobelPrize(
    val id: String,
    val year: String,
    val category: String,
    val laureates: List<Laureate>
)


data class Laureate(
    val id: String,
    val fullName: String,
    val portion: String,
    val motivation: String,
    val birthCountry: String?,
    val birthPlace: String?,
    val portraitUrl: String?
)