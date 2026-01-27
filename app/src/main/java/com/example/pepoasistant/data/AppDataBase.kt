package com.example.pepoasistant.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun transactionDao(): TransactionDao

}