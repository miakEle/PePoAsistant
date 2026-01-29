package com.example.pepoasistant.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pepoasistant.domain.entities.Transaction
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

class TransactionViewModel(
    private val repository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
    private val mapper: CategoryUiMapper
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getAllTransactions().collect {
                Log.d("TransactionViewModel", "getAllTransactions")
            }
        }
    }


    private val _state = MutableStateFlow<List<CategoryUi>>(emptyList())
    val state: StateFlow<List<CategoryUi>> = _state

    fun getAllCategoriesByType(type: TypeOfCategory){
        viewModelScope.launch {
            categoryRepository.getCategoriesByType(type)
                .map { list-> list.map { mapper.map(it) } }
                .collect { uiList -> _state.value = uiList}
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
