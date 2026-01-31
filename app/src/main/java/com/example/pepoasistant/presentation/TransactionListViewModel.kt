package com.example.pepoasistant.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TransactionListViewModel(
    private val repo: TransactionRepository
) : ViewModel(){

    private val _transactionListGrouped = MutableStateFlow<List<TransactionListItem>>(emptyList())
    val transactionListGrouped: StateFlow<List<TransactionListItem>> = _transactionListGrouped

    init {
        viewModelScope.launch {
            repo.getAllTransactions()
                .map { list ->
                    list.sortedByDescending { it.date }
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
                }
                .collect { groupedList ->
                    _transactionListGrouped.value = groupedList
                }
        }
    }


}