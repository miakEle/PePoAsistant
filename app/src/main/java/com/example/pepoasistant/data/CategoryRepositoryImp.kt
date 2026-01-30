package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImp(
    private val dao: CategoryDao
): CategoryRepository {
    override fun getAllCategories(): Flow<List<Category>> =
        dao.getAllCategories().map { list ->  list.map { it.toDomain() }}


    override suspend fun addCategory(category: Category) {
        dao.insertCategory(category.toEntity())
    }

    override suspend fun deleteCategory(categoryId: Long) {
        dao.deleteCategory(categoryId)
    }

    override fun getCategoriesByType(type: TypeOfCategory): Flow<List<Category>> =
        dao.getCategoriesByType(type).map { list -> list.map { it.toDomain() }}

}
