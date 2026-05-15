package com.example.a62.data.repository

import com.example.a62.data.model.RemoteNobelResponseRetrofit
import com.example.a62.data.model.toDomain
import com.example.a62.data.remote.RetrofitClient
import com.example.a62.data.model.toDomainList
import com.example.a62.domain.model.Laureate
import com.example.a62.domain.repository.LaureateRepository


class RetrofitLaureateRepositoryImpl : LaureateRepository {
    override suspend fun filterLaureates(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<Laureate> {
        return try {
            val resp = RetrofitClient.api.getLaureats()
            if (!resp.isSuccessful) return emptyList()
            val body = resp.body() ?: RemoteNobelResponseRetrofit()
            val all = body.nobelPrizes.flatMap { it.toDomain() }
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
