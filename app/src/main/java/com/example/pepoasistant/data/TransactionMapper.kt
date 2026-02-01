package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Transaction
import java.time.LocalDate

fun TransactionEntity.toDomain(): Transaction =
    Transaction(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = LocalDate.ofEpochDay(date),
        note = note
    )

fun Transaction.toEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = date.toEpochDay(),
        note = note
    )