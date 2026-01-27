package com.example.pepoasistant.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAll(): Flow<List<TransactionEntity>>


    @Query(
        """SELECT * FROM transactions
        WHERE strftime('%Y', date) = :year
        AND strftime('%m', date) = :month
        ORDER BY date DESC
    """
    )
    fun getForMonth(year: Int, month: Int): Flow<List<TransactionEntity>>

    @Insert
    suspend fun insert(entity: TransactionEntity)

    @Update
    suspend fun update(entity: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id=:id")
    suspend fun delete(id: Long)
}