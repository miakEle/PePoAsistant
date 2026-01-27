package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Transaction

fun TransactionEntity.toDomain(): Transaction =
    Transaction(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = date,
        note = note
    )

fun Transaction.toEntity(): TransactionEntity =
    TransactionEntity(
        id = id,
        categoryId = categoryId,
        amount = amount,
        date = date,
        note = note
    )