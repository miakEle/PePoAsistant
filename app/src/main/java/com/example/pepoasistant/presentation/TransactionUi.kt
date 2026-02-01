package com.example.pepoasistant.presentation

import java.time.LocalDate

data class TransactionUi(
    val id: Long,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: Long,
    val amount: Double,
    val date: LocalDate,
    val note: String?
)


