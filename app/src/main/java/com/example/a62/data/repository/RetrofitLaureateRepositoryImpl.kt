package com.example.a62.data.repository

import com.example.a62.data.model.RemoteNobelResponseRetrofit
import com.example.a62.data.model.toDomainList
import com.example.a62.data.remote.RetrofitClient
import com.example.a62.data.model.toDomainList
import com.example.a62.domain.model.NobelPrize
import com.example.a62.domain.repository.LaureateRepository


class RetrofitLaureateRepositoryImpl : LaureateRepository {
    override suspend fun filterLaureates(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize> {
        return try {
            val resp = RetrofitClient.api.getLaureats()
            if (!resp.isSuccessful) return emptyList()
            val body = resp.body() ?: RemoteNobelResponseRetrofit()
            val all = body.nobelPrizes.flatMap { it.toDomainList() }
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
