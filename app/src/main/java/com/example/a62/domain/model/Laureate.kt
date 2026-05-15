package com.example.a62.domain.model

import com.example.a62.data.model.Localized

data class Laureate(
    val id: String,
    val fullName: String,
    val year: String,
    val category: String,
    val motivation: String,
    val birthCountry: String?,
    val birthPlace: String?,
    val portraitUrl: String?
)


//data class Laureate(
//    val id: String,
//    val fullName: String,
//    val year: String,
//    val category: String,
//    val motivation: String,
//    val birthCountry: String?,
//    val birthPlace: Localized?,
//    val portraitUrl: String?
//)
