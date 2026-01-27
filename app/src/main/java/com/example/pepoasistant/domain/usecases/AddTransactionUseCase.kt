package com.example.pepoasistant.domain.usecases

import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository

class AddTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: Transaction) {
        repository.addTransaction(transaction)
    }
}