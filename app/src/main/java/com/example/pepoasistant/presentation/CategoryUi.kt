package com.example.pepoasistant.presentation

data class CategoryUi(
    val id: Long,
    val name: String,
    val icon: Int,
    val isSelected: Boolean = false
) {}