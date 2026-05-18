package com.example.a66.domain.repository

import com.example.a66.domain.model.User
import com.example.a66.domain.model.NobelPrize

interface UserRepository {
    suspend fun getProfile(): User
    suspend fun showFavorite(): List<NobelPrize>
    suspend fun addFavourite(prize: NobelPrize): User
    suspend fun removeFavourite(prizeId: Int): User
}

