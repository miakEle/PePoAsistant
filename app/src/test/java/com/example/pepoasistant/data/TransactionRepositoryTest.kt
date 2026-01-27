package com.example.pepoasistant.data

import com.example.pepoasistant.domain.entities.Transaction
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class TransactionRepositoryTest {

    private val dao = FakeDao()
    private val repo = TransactionRepositoryImp(dao)

    @Test
    fun add_and_get_transactions() = runBlocking {
        val t = Transaction(
            id = 0,
            categoryId = 1,
            amount = 5.0,
            date = LocalDate.of(2024, 1, 1),
            note = "test note"
        )

        repo.addTransaction(t)

        val result = repo.getAllTransactions().first()

        assertEquals(1, result.size)
        assertEquals("test note", result[0].note)
        assertEquals(5.0, result[0].amount, 0.0)
    }

    @Test
    fun get_transactions_for_month_filters_correctly() = runBlocking {
        val jan = Transaction(
            id = 0,
            categoryId = 1,
            amount = 10.0,
            date = LocalDate.of(2024, 1, 10)
        )
        val feb = Transaction(
            id = 0,
            categoryId = 1,
            amount = 20.0,
            date = LocalDate.of(2024, 2, 5)
        )

        repo.addTransaction(jan)
        repo.addTransaction(feb)

        val janResult = repo.getTransactionsForMonth(2024, 1).first()

        assertEquals(1, janResult.size)
        assertEquals(10.0, janResult[0].amount, 0.0)
    }

    @Test
    fun edit_and_delete_transaction() = runBlocking {
        val t = Transaction(
            id = 0,
            categoryId = 1,
            amount = 5.0,
            date = LocalDate.of(2024, 1, 1)
        )

        repo.addTransaction(t)
        var all = repo.getAllTransactions().first()
        val saved = all.first()

        val edited = saved.copy(amount = 15.0)
        repo.editTransaction(edited)

        all = repo.getAllTransactions().first()
        assertEquals(1, all.size)
        assertEquals(15.0, all[0].amount, 0.0)

        repo.deleteTransaction(all[0].id)

        all = repo.getAllTransactions().first()
        assertEquals(0, all.size)
    }
}