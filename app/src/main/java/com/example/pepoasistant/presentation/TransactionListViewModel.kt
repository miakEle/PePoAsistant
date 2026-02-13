package com.example.pepoasistant.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class TransactionListViewModel(
    private val repo: TransactionRepository,
    private val repoCategory: CategoryRepository
) : ViewModel(){

    private val mapper = TransactionUiMapper()

    private val _selectedYear = MutableStateFlow(LocalDate.now().year)
    val selectedYear = _selectedYear.asStateFlow()

    private val _selectedMonth = MutableStateFlow(LocalDate.now().monthValue)
    val selectedMonth = _selectedMonth.asStateFlow()

    private val _transactionListGrouped = MutableStateFlow<List<TransactionListItem>>(emptyList())
    val transactionListGrouped: StateFlow<List<TransactionListItem>> = _transactionListGrouped

    init {
        viewModelScope.launch {
            combine(
                repo.getAllTransactions(),
                repoCategory.getAllCategories()
            ) { transactions, categories ->

                val categoryMap = categories.associateBy { it.id }

                val uiList = transactions.map { tx ->
                    val category = categoryMap[tx.categoryId]
                        ?: error("Category not found for id ${tx.categoryId}")
                    mapper.toUi(tx, category)
                }

                uiList
                    .sortedByDescending { it.date }
                    .groupBy { it.date.withDayOfMonth(1) }
                    .flatMap { (monthDate, monthItems) ->

                        val monthHeader = TransactionListItem.MonthHeader(
                            monthDate.year,
                            monthDate.monthValue
                        )

                        val days = monthItems
                            .groupBy { it.date }
                            .flatMap { (day, dayItems) ->
                                buildList {
                                    add(TransactionListItem.DayHeader(day))
                                    addAll(dayItems.map { TransactionListItem.TransactionRow(it) })
                                }
                            }

                        buildList {
                            add(monthHeader)
                            addAll(days)
                        }
                    }
            }.collect { groupedList ->
                _transactionListGrouped.value = groupedList
            }
        }

    }

    fun setSelectedYearAndMonth(year: Int, month: Int) {
        _selectedYear.value = year
        _selectedMonth.value = month

    }


}