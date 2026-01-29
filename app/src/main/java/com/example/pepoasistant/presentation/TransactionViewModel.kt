package com.example.pepoasistant.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionViewModel(
    private val repository: TransactionRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                Log.d("TransactionViewModel", "getAllTransactions")
            }
        }
    }

    fun insert(
        categoryId: Long,
        amount: Double,
        date: LocalDate,
        note: String?
    ) {
        viewModelScope.launch {
            repository.addTransaction(
                Transaction(
                    categoryId = categoryId,
                    amount = amount,
                    date = date,
                    note = note
                )
            )
        }
    }
}
