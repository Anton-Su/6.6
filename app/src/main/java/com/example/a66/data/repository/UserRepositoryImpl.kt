package com.example.a66.data.repository

import com.example.a66.data.model.toDomain
import com.example.a66.data.remote.KtorClient
import com.example.a66.domain.model.NobelPrize
import com.example.a66.domain.model.User
import com.example.a66.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {
    override suspend fun getProfile(): User {
        return KtorClient.getProfile().toDomain()
    }

    override suspend fun showFavorite(): List<NobelPrize> {
        return KtorClient.getFavoritePrizes().map { it.toDomain() }
    }

    override suspend fun addFavourite(prize: NobelPrize): User {
        KtorClient.addPrize(prize.id)
        return getProfile()
    }

    override suspend fun removeFavourite(prizeId: String): User {
        KtorClient.deletePrize(prizeId)
        return getProfile()
    }



}

