package com.example.a66.data.repository

import com.example.a66.data.remote.KtorClient
import com.example.a66.data.model.toDomain
import com.example.a66.domain.model.NobelPrize
import com.example.a66.domain.repository.NobelPrizeRepository

class KtorNobelPrizeRepositoryImpl : NobelPrizeRepository {
    override suspend fun filterNobelPrize(
        nobelPrizeYear: Int?,
        nobelPrizeCategory: String?
    ): List<NobelPrize> {
        return try {
            val response = KtorClient.fetchLaureates()
            val all = response.map { it.toDomain() }
            all.filter { p ->
                val yearMatch = nobelPrizeYear?.let { y -> p.year == y.toString() } ?: true
                val catMatch = nobelPrizeCategory?.let { c -> c.isBlank() || p.category.equals(c, ignoreCase = true) } ?: true
                yearMatch && catMatch
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}


