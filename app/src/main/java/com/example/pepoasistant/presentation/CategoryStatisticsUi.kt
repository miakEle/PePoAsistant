package com.example.pepoasistant.presentation

data class CategoryStatisticsUi(
    val categoryName: String,
    val categoryIcon: String,
    val amount: Double,
    val percents: Float,
    val color: Int
) {
}