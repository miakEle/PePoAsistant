package com.example.pepoasistant.domain.usecases

import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase(
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<List<Transaction>> =
        repository.getAllTransactions()

}