package com.example.pepoasistant.domain.entities

import android.icu.util.LocaleData

data class Transaction(
    val id: Long,
    val amount: Double,
    val categoryId: Long,
    val data: LocaleData,
    val note: String? = null
) {
}