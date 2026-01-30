package com.example.pepoasistant.domain.entities

data class SuperCategoryTotal(
    val superCategory: SuperCategory,
    val totalAmount: Double,
    val percentage: Double
) {
}