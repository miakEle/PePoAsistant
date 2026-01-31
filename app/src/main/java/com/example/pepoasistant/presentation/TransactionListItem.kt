package com.example.pepoasistant.presentation

import com.example.pepoasistant.domain.entities.Transaction
import java.time.LocalDate

sealed class TransactionListItem {

    data class MonthHeader(val year: Int, val month: Int): TransactionListItem()

    data class DayHeader(val date: LocalDate) : TransactionListItem()

    data class TransactionRow(val transaction: Transaction) : TransactionListItem()
}