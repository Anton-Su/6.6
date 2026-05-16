package com.example.a62.domain.repository

import com.example.a62.domain.model.NobelPrize

interface NobelPrizeRepository {
    suspend fun filterNobelPrize(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize>
}
