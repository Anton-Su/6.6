package com.example.a66.data.model


import com.example.a66.domain.model.NobelPrize
import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeDto(
    val id: String,
    val year: String,
    val category: String,
    val laureates: List<LaureateDto> = emptyList()
)


fun NobelPrizeDto.toDomain(): NobelPrize = NobelPrize(
    id = id,
    year = year,
    category = category,
    laureates = laureates.map { it.toDomain() }
)


