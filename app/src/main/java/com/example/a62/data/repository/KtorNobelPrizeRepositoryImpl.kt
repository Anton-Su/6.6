package com.example.a62.data.repository

import com.example.a62.data.remote.KtorClient
import com.example.a62.data.model.toDomainList
import com.example.a62.domain.model.NobelPrize
import com.example.a62.domain.repository.NobelPrizeRepository

class KtorNobelPrizeRepositoryImpl : NobelPrizeRepository {
    override suspend fun filterNobelPrize(
        nobelPrizeYear: Int?,
        nobelPrizeCategory: String?
    ): List<NobelPrize> {
        val response = KtorClient.fetchLaureates()
        val all = response.nobelPrizes.flatMap { it.toDomainList() }
        return all.filter { p ->
            val yearMatch = nobelPrizeYear?.let { y -> p.year == y.toString() } ?: true
            val catMatch = nobelPrizeCategory?.let { c -> c.isBlank() || p.category.equals(c, ignoreCase = true) } ?: true
            yearMatch && catMatch
        }
    }
}

