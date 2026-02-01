package com.example.pepoasistant.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class
               ],
    version = 6, exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDataBase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao


}