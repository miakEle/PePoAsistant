package com.example.pepoasistant.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDao : TransactionDao {

    private val list = mutableListOf<TransactionEntity>()

    override fun getAll(): Flow<List<TransactionEntity>> =
        flow { emit(list.toList()) }

    override fun getForMonth(year: String, month: String): Flow<List<TransactionEntity>> =
        flow {
            val filtered = list.filter {
                it.date.year.toString() == year &&
                        "%02d".format(it.date.monthValue) == month
            }
            emit(filtered)
        }

    override suspend fun insert(entity: TransactionEntity) {
        // simulate autoGenerate = true
        val newId = if (list.isEmpty()) 1L else (list.maxOf { it.id } + 1)
        list.add(entity.copy(id = newId))
    }

    override suspend fun update(entity: TransactionEntity) {
        val index = list.indexOfFirst { it.id == entity.id }
        if (index != -1) list[index] = entity
    }

    override suspend fun delete(id: Long) {
        list.removeIf { it.id == id }
    }
}