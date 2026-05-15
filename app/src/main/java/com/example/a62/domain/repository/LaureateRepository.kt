package com.example.a62.domain.repository

import com.example.a62.domain.model.Laureate

interface LaureateRepository {
    suspend fun filterLaureates(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<Laureate>
}

