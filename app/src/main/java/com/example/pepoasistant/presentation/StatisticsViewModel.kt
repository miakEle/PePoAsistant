package com.example.pepoasistant.presentation

import androidx.lifecycle.ViewModel
import com.example.pepoasistant.domain.entities.SuperCategory
import com.example.pepoasistant.domain.entities.TypeOfCategory
import com.example.pepoasistant.domain.repositories.CategoryRepository
import com.example.pepoasistant.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class StatisticsViewModel(
    private val repository: TransactionRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    val pieData: Flow<List<PieSliceUi>> =
        combine(
            repository.getAllTransactions(),
            categoryRepository.getAllCategories()
        ) { transactions, categories ->

            val categoryMap = categories.associateBy { it.id }

            // Only expenses should be counted in Needs/Wants/Savings
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