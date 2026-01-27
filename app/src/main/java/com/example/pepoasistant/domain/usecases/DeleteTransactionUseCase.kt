package com.example.pepoasistant.domain.usecases

import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(id: Long) {
        repository.deleteTransaction(id)
    }
}