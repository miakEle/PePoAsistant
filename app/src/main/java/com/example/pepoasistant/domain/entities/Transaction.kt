package com.example.pepoasistant.domain.entities

import android.icu.util.LocaleData
import java.time.LocalDate

data class Transaction(
    var id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val date: LocalDate,
    val note: String? = null
) {
}