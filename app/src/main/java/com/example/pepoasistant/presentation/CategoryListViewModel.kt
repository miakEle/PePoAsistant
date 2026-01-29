package com.example.pepoasistant.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class CategoryListViewModel(
    private val repository: CategoryRepository,
    private val mapper: CategoryUiMapper
): ViewModel() {

    private val _state = MutableStateFlow<List<CategoryUi>>(emptyList())
    val state: StateFlow<List<CategoryUi>> = _state

    fun getAllCategoriesByType(type: TypeOfCategory){
        viewModelScope.launch {
            repository.getCategoriesByType(type)
                .map { list-> list.map { mapper.map(it) } }
                .collect { uiList -> _state.value = uiList}
        }
    }
}