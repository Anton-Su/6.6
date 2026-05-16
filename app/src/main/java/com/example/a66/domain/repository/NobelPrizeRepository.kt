package com.example.a66.domain.repository

import com.example.a66.domain.model.NobelPrize

interface NobelPrizeRepository {
    suspend fun filterNobelPrize(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize>
}

