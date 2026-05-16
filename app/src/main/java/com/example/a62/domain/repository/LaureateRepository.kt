package com.example.a62.domain.repository

import com.example.a62.domain.model.NobelPrize

interface LaureateRepository {
    suspend fun filterLaureates(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize>
}

