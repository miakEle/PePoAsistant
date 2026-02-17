package com.example.pepoasistant.presentation

import com.example.pepoasistant.domain.entities.TypeOfCategory

data class CategoryUi(
    val id: Long,
    val name: String,
    val icon: Int,
    val type: TypeOfCategory,
    val isSelected: Boolean = false
) {}