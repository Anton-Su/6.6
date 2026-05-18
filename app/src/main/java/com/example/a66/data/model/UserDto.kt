package com.example.a66.data.model

import com.example.a66.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val username: String,
    val gender: String,
    val age: Int,
    val favoritePrizes: List<NobelPrizeDto>
)


fun UserDto.toDomain(): User = User(
    id = id,
    username = username,
    gender = gender,
    age = age,
    favoritePrizes = favoritePrizes.map { it.toDomain() }
)