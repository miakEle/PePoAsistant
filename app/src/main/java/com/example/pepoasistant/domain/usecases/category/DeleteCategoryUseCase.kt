package com.example.pepoasistant.domain.usecases.category

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.repositories.CategoryRepository

class DeleteCategoryUseCase(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category){
        repository.deleteCategory(category.id)
    }
}