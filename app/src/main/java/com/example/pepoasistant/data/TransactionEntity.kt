package com.example.pepoasistant.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity("transactions")
class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val date: LocalDate,// room 2,5+ -> LocalDate by TypeConverter
    val note: String?
) {
}