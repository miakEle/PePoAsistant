package com.example.pepoasistant.domain.usecases.category

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

class GetAllCategoriesUseCase(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<List<Category>> = repository.getAllCategories()
}