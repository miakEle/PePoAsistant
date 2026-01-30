package com.example.pepoasistant.domain.repositories

import com.example.pepoasistant.domain.entities.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAllTransactions(): Flow<List<Transaction>>

    fun getTransactionsForMonth(year: Int, month: Int): Flow<List<Transaction>>

    suspend fun addTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transactionId: Long)

    suspend fun editTransaction(transaction: Transaction)
}