package com.example.pepoasistant.domain.entities

enum class SuperCategory {
    NEEDS, WANTS, SAVINGS, SALARY
}

enum class TypeOfCategory {
    INCOME, EXPENSE
}

data class Category(
    val id: Long = 0,
    val name: String,
    val icon: String,
    val color: Long,
    val superCategory: SuperCategory,
    val type: TypeOfCategory
) {
}