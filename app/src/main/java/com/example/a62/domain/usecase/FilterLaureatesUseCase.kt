package com.example.a62.domain.usecase

import com.example.a62.domain.model.NobelPrize
import com.example.a62.domain.repository.LaureateRepository

class FilterLaureatesUseCase(private val repository: LaureateRepository) {
    suspend operator fun invoke(nobelPrizeYear: Int?, nobelPrizeCategory: String?): List<NobelPrize> {
        return repository.filterLaureates(nobelPrizeYear, nobelPrizeCategory)
    }
}

