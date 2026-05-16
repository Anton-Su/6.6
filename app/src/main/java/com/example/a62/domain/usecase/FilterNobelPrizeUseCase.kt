package com.example.a62.domain.usecase

import com.example.a62.domain.model.NobelPrize
import com.example.a62.domain.repository.NobelPrizeRepository
class FilterNobelPrizeUseCase(private val repository: NobelPrizeRepository) {
    suspend operator fun invoke(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize> {
        return repository.filterNobelPrize(nobelPrizeYear, nobelPrizeCategory)
    }
}

