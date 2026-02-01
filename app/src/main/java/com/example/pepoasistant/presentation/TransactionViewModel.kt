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

    private var isSelectedId: Long = 0

    private val _typeOfCategory = MutableStateFlow<TypeOfCategory>(TypeOfCategory.EXPENSE)
    val typeOfCategory: StateFlow<TypeOfCategory> = _typeOfCategory


    private val _state = MutableStateFlow<List<CategoryUi>>(emptyList())
    val state: StateFlow<List<CategoryUi>> = _state

    fun selectType(t: TypeOfCategory) {
        _typeOfCategory.value = t
    }

    fun getAllCategoriesByType() {
        viewModelScope.launch {
            categoryRepository.getCategoriesByType(_typeOfCategory.value)
                .map { list -> list.map { mapper.map(it) } }
                .collect { uiList -> _state.value = uiList }
        }
    }

    fun onCategoryClicked(id: Long) {
        isSelectedId = id
        _state.value = _state.value.map { item ->
            item.copy(isSelected = item.id == id)
        }
    }

    fun insert(
        amount: Double,
        date: LocalDate,
        note: String?
    ) {
        viewModelScope.launch {
            repository.addTransaction(
                Transaction(
                    categoryId = isSelectedId,
                    amount = amount,
                    date = date,
                    note = note
                )
            )
        }
    }
}
