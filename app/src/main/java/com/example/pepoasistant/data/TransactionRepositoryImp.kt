package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImp(
    private val dao: TransactionDao
) : TransactionRepository {
    override fun getAllTransactions(): Flow<List<Transaction>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }


    override fun getTransactionsForMonth(
        year: Int,
        month: Int
    ): Flow<List<Transaction>> =
        dao.getForMonth(
            year.toString(),
            "%02d".format(month)
        ).map { list -> list.map { it.toDomain() } }




    override suspend fun addTransaction(transaction: Transaction) {
        dao.insert(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transactionId: Long) {
        dao.delete(transactionId)
    }

    override suspend fun editTransaction(transaction: Transaction) {
        dao.update(transaction.toEntity())
    }
}