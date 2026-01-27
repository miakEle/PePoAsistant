package com.example.pepoasistant.domain.entities

import android.icu.util.LocaleData

data class Transaction(
    val id: Long,
    val categoryId: Long,
    val amount: Double,
    val data: LocaleData,
    val note: String? = null
) {
}