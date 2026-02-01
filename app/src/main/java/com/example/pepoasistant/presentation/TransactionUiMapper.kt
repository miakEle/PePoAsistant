package com.example.pepoasistant.presentation

import com.example.pepoasistant.domain.entities.Category
import com.example.pepoasistant.domain.entities.Transaction
import java.time.format.DateTimeFormatter

class TransactionUiMapper {

    fun toUi(transaction: Transaction, category: Category): TransactionUi =
        TransactionUi(
            id = transaction.id,
            categoryName = category.name,
            categoryIcon = category.icon,
            categoryColor = category.color,
            amount = transaction.amount,
            date = transaction.date,
            note = transaction.note
        )

}