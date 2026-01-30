package com.example.pepoasistant.domain.repositories

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.entities.TypeOfCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategories(): Flow<List<Category>>

    suspend fun addCategory(category: Category)

    suspend fun deleteCategory(categoryId: Long)

    fun getCategoriesByType(type: TypeOfCategory): Flow<List<Category>>

}