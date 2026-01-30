package com.example.pepoasistant.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository

class TransactionViewModelFactory(
    private val transactionRepo: TransactionRepository,
    private val categoryRepo: CategoryRepository,
    private val mapper: CategoryUiMapper

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(transactionRepo, categoryRepo,mapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}