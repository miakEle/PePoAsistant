package com.example.pepoasistant.presentation

import androidx.lifecycle.ViewModel
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

class StatisticsViewModel(
    private val repository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {


    private val _periodItems = MutableStateFlow<List<PeriodListItem>>(emptyList())
    val periodItems = _periodItems.asStateFlow()

    private val monthsNames = listOf(
        "tammi",
        "helmi",
        "maalis",
        "huhti",
        "touko",
        "kesä",
        "heinä",
        "elo",
        "syys",
        "loka",
        "marras",
        "joulu"
    )

    private val offsets = mutableListOf(0)
    private var selectedOffset = 0

    init {
        loadMore()
    }

    fun loadMore()
    {
        val lastOffset = offsets.last()
        for (i in 1 until 5){
            offsets.add(lastOffset - i)
        }
        updateList()
    }

    private fun updateList() {
        val now = LocalDate.now()

        val list = offsets.map { offset ->
            val date = now.plusMonths(offset.toLong())
            PeriodListItem.Period(
                offset,
                "${monthsNames[date.monthValue - 1]} ${date.year}",
                date.year,
                date.monthValue,
                selectedOffset == offset
            )
        } + PeriodListItem.AddMore

        _periodItems.value = list
    }

    fun selectItem (item: PeriodListItem.Period){
        selectedOffset = item.offset
        updateList()
    }

    val pieData: Flow<List<PieSliceUi>> =
        combine(
            repository.getAllTransactions(),
            categoryRepository.getAllCategories()
        ) { transactions, categories ->

            val categoryMap = categories.associateBy { it.id }

            val expenseTransactions = transactions.filter {
                categoryMap[it.categoryId]?.type == TypeOfCategory.EXPENSE
            }

            val totalAmount = expenseTransactions.sumOf { it.amount }.coerceAtLeast(1.0)

            var wantsAmount = 0.0
            var needsAmount = 0.0
            var savingsAmount = 0.0

            expenseTransactions.forEach { tx ->
                val category = categoryMap[tx.categoryId] ?: return@forEach

                when (category.superCategory) {
                    SuperCategory.WANTS -> wantsAmount += tx.amount
                    SuperCategory.NEEDS -> needsAmount += tx.amount
                    SuperCategory.SAVINGS -> savingsAmount += tx.amount
                    else -> Unit
                }
            }

            listOf(
                PieSliceUi(
                    categoryName = "Needs",
                    percent = (needsAmount / totalAmount).toFloat(),
                    amount = needsAmount,
                    color = 0xFFDCE775.toInt()
                ),
                PieSliceUi(
                    categoryName = "Wants",
                    percent = (wantsAmount / totalAmount).toFloat(),
                    amount = wantsAmount,
                    color = 0xFFF06292.toInt()
                ),
                PieSliceUi(
                    categoryName = "Savings",
                    percent = (savingsAmount / totalAmount).toFloat(),
                    amount = savingsAmount,
                    color = 0xFF4DB6AC.toInt()
                )
            )
        }

    val categoryStatisticsData: Flow<List<CategoryStatisticsUi>> =
        combine(
            repository.getAllTransactions(),
            categoryRepository.getAllCategories()
        ) { transactions, categories ->

            val categoryMap = categories.associateBy { it.id }

            // Only EXPENSES (recommended)
            val expenseTransactions = transactions.filter {
                categoryMap[it.categoryId]?.type == TypeOfCategory.EXPENSE
            }

            val totalAmount = expenseTransactions.sumOf { it.amount }.coerceAtLeast(1.0)

            expenseTransactions
                .groupBy { it.categoryId }
                .map { (categoryId, listOfTransactions) ->

                    val category = categoryMap[categoryId]!!
                    val sum = listOfTransactions.sumOf { it.amount }

                    CategoryStatisticsUi(
                        categoryName = category.name,
                        categoryIcon = category.icon,
                        amount = sum,
                        percents = (sum / totalAmount).toFloat(),
                        color = category.color.toInt()
                    )
                }
                .sortedByDescending { it.amount } // FIXED: now sorting works
        }

}