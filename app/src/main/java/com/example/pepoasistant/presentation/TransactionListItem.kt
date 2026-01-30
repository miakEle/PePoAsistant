package com.example.pepoasistant.presentation

import java.time.LocalDate

sealed class TransactionListItem {

    data class Header(val date: String) : TransactionListItem()

    data class Item(
        val id: Long,
        val categoryName: String,
        val amount: String,
        val date: LocalDate
    ) : TransactionListItem()
}