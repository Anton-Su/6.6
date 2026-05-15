package com.example.a62.data.repository

import com.example.a62.data.remote.KtorClient
import com.example.a62.data.model.toDomainList
import com.example.a62.domain.model.Laureate
import com.example.a62.domain.repository.LaureateRepository

class KtorLaureateRepositoryImpl : LaureateRepository {
    override suspend fun filterLaureates(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<Laureate> {
        return try {
            val body = KtorClient.fetchLaureates()
            val all = body.nobelPrizes.flatMap { it.toDomainList() }
            all.filter { l ->
                val yearMatch = nobelPrizeYear?.let { y -> l.year == y.toString() } ?: true
                val catMatch = nobelPrizeCategory?.let { c -> c.isBlank() || l.category.equals(c, ignoreCase = true) } ?: true
                yearMatch && catMatch
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

