package com.example.pepoasistant.domain.entities

data class BudgetRule (
    val needsLimit: Double = 0.50,
    val wantsLimit: Double = 0.30,
    val savingsLimit: Double = 0.20,

){
}