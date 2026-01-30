package com.example.pepoasistant.domain.usecases.transaction

import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsForMonthUseCase(
    private val repository: TransactionRepository
) {

    operator fun invoke(year: Int, month: Int): Flow<List<Transaction>> =
        repository.getTransactionsForMonth(year, month)
}