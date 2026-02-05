package com.example.pepoasistant.presentation

data class CategoryStatisticsUi(
    val categoryName: String,
    val categoryIcon: Int,
    val amount: Double,
    val percents: Float,
    val color: Int
) {
}