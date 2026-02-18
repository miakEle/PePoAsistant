package com.example.pepoasistant.domain.usecases.category

import com.example.pepoasistant.domain.repositories.CategoryRepository

class GetCategoryByIdUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(id: Long) = repository.getCategoryById(id)
}
