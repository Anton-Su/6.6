package com.example.a62.data.repository

import com.example.a62.data.remote.KtorClient
import com.example.a62.data.model.toDomainList
import com.example.a62.domain.model.Laureate
import com.example.a62.domain.repository.LaureateRepository

class KtorLaureateRepositoryImpl : LaureateRepository {
    override suspend fun filterLaureates(
        nobelPrizeYear: Int?,
        nobelPrizeCategory: String?
    ): List<Laureate> {
        val response = KtorClient.fetchLaureates()
        val all = response.nobelPrizes.flatMap {
            it.toDomainList()
        }
        return all.filter { l ->
            val yearMatch = nobelPrizeYear?.let {
                l.year == it.toString()
            } ?: true
            val catMatch = nobelPrizeCategory?.let {
                it.isBlank() || l.category.equals(it, true)
            } ?: true
            yearMatch && catMatch
        }
    }
}

